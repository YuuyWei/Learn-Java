package com.yuuy.spring.framework;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;

public class MyApplicationContext {
    // 配置类
    private Class configClass;

    // 单例池
    private Map<String, Object> singletonPools = new ConcurrentHashMap<>();

    // Bean 定义表
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    // BeanPostProcessor 列表
    private List<String> beanPostProcessors = new CopyOnWriteArrayList<>();

    public MyApplicationContext(Class configClass) {
        this.configClass = configClass;

        scan(configClass);

        // 必须等扫描完成才创建单例池
        createSingleton();
    }

    /**
     * 遍历beanDefinitionMap
     * 如果Bean的Scope是Singleton那么就创建Bean
     */
    private void createSingleton() {
        for (String beanName : beanDefinitionMap.keySet()) {
            BeanDefinition definition = beanDefinitionMap.get(beanName);
            if (definition.getScope() == "singleton") {
                singletonPools.put(beanName, createBean(definition, beanName));
            }
        }
    }

    /**
     * 解析配置类
     * 1. 获取扫描路径
     *
     * 2. 获取Component所注解的类
     *
     * 3. 判断Component的Scope, 定义Bean
     * if Prototype -> 原型Bean
     * else -> 单例Bean
     *
     * @param configClass
     */
    private void scan(Class configClass) {

        // 获取扫描路径
        Path path = null;
        try {
            path = getScanPath(configClass);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // 获取Component所注解的类
        if (Files.isDirectory(path)) {
            try {
                Files.list(path)
                        .filter(f -> f.toString().endsWith(".class"))
                        .map( f -> getClass(f))
                        .filter(clazz -> clazz.isAnnotationPresent(Component.class))
                        .forEach(clazz -> register(clazz));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void register(Class<?> clazz) {
        String componentName = clazz.getDeclaredAnnotation(Component.class).value();
        if (componentName.isBlank()) {
            componentName = Pattern.compile("^([A-Z])")
                    .matcher(clazz.getSimpleName())
                    .replaceAll(matchResult -> matchResult.group(1).toLowerCase(Locale.ROOT));
        }

        registerBeanDefinition(clazz, componentName);
        registerBeanPostProcessor(clazz, componentName);
    }

    private void registerBeanPostProcessor(Class<?> clazz, String componentName) {
        if (BeanPostProcessor.class.isAssignableFrom(clazz)) {
            beanPostProcessors.add(componentName);
        }
    }

    /**
     * 根据Scope注解确定类的定义
     * singleton -> BeanDefinition("singleton")
     * prototype -> BeanDefinition("prototype")
     *
     * 添加到beanDefinitionMap
     * @param clazz
     */
    private void registerBeanDefinition(Class<?> clazz, String componentName) {
        String scope = "singleton";

        if (clazz.getDeclaredAnnotation(Scope.class) != null) {
            String value = clazz.getDeclaredAnnotation(Scope.class).value();
            if (value.equals("prototype")) {
                scope = value;
            }
        }

        beanDefinitionMap.put(componentName, new BeanDefinition(scope, clazz));
    }

    /**
     * 通过文件名获取类名
     * 1. 获取完整类名
     * 找到相对路径 -> 获得路径分隔符并且替换成 .
     *
     * 2. 加载类
     * 获取类加载器 -> 传递类名从而得到类
     *
     * @param file
     * @return Class
     */
    private static Class<?> getClass(Path file) {
        // 获取类名
        String className = file.toString().substring(
                file.toString().indexOf("com"),
                file.toString().indexOf(".class")
        ).replace(FileSystems.getDefault().getSeparator(), ".");

        ClassLoader classLoader = MyApplicationContext.class.getClassLoader();
        Class<?> clazz = null;
        try {
            clazz = classLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz;
    }

    /**
     * 解析配置类
     * 1. 解析ComponentScan注解，得到扫描路径
     * 这里利用反射机制，获取到类上的注解
     *
     * 2. 转换为Path
     * 获取到ClassLoader -> 获取制定目录下的资源 -> 将资源转换为Path
     *
     * 总结
     * 1. 反射获取注解
     * 2. 类加载器获取资源
     *
     * @param configClass
     * @return
     * @throws URISyntaxException
     */
    private Path getScanPath(Class configClass) throws URISyntaxException {
        // 获得注解上的路径
        ComponentScan componentScanAnnotation = (ComponentScan) configClass.getDeclaredAnnotation(ComponentScan.class);
        String path = componentScanAnnotation.value();

        // 获取目录绝对路径
        ClassLoader classLoader = MyApplicationContext.class.getClassLoader();
        URL resource = classLoader.getResource(path.replace('.', '/'));
        return Paths.get(resource.toURI());
    }

    /**
     * 获取指定名字的Bean
     * 判断Bean的Scope
     * 单例 -> 从单例池返回
     * 原型 -> 创建后返回
     *
     * @param beanName
     * @return
     */
    public Object getBean(String beanName) {
        if (beanDefinitionMap.containsKey(beanName)) {
            BeanDefinition definition = beanDefinitionMap.get(beanName);
            if (definition.getScope().equals("singleton")) {
                return singletonPools.get(beanName);
            } else {
                return createBean(definition, beanName);
            }
        } else {
            throw new NullPointerException();
        }
    }

    /**
     * 根据 bean definition 创建原型Bean
     *
     * 实现依赖注入
     *
     * 1. 调用构造函数创建 Bean
     *
     * 2. 找出 @Autowired 修饰的字段
     *
     * 3. 调用 getBean 方法获取实例
     *
     * 4. 使用set方法将值注入
     * 如果是私有变量还要setAccessible(true)
     *
     * @param definition
     * @param beanName
     * @return
     */
    private Object createBean(BeanDefinition definition, String beanName) {
        Class clazz = definition.getClazz();

        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();

            for (Field declaredField : clazz.getDeclaredFields()) {
                if (declaredField.isAnnotationPresent(Autowired.class)) {
                    Object bean = getBean(declaredField.getName());
                    if (bean == null) {
                        throw new Exception("找不到Bean");
                    }
                    declaredField.setAccessible(true); // 不加这个会报错：无法修改私有属性
                    declaredField.set(instance, bean);
                }
            }

            // Aware 回调
            if (instance instanceof  BeanNameAware) {
                ((BeanNameAware) instance).setBeanName(beanName);
            }

            // 初始化前
            for (String beanPostProcessorName : beanPostProcessors) {
                BeanPostProcessor beanPostProcessor = (BeanPostProcessor) getBean(beanPostProcessorName);
                if (beanPostProcessor == null) {

                } else {
                    beanPostProcessor.postProcessorBeforeInitialization(beanName, instance);
                }
            }

            // 初始化
            if (instance instanceof InitializeBean) {
                ((InitializeBean) instance).afterPropertiesSet();
            }

            // 初始化后
            for (String beanPostProcessorName : beanPostProcessors) {
                BeanPostProcessor beanPostProcessor = (BeanPostProcessor) getBean(beanPostProcessorName);
                if (beanPostProcessor == null) {

                } else {
                    beanPostProcessor.postProcessorAfterInitialization(beanName, instance);
                }
            }

            return instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
