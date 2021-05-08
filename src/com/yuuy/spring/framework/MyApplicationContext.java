package com.yuuy.spring.framework;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.concurrent.ConcurrentHashMap;

public class MyApplicationContext {
    // 配置类
    private Class configClass;

    // 单例池
    private ConcurrentHashMap<String, Object> singletonPools = new ConcurrentHashMap<>();

    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public MyApplicationContext(Class configClass) {
        this.configClass = configClass;

        scan(configClass);

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
                singletonPools.put(beanName, createBean(definition));
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
                        .forEach(clazz -> addToBeanDefinitionMap(clazz));
            } catch (IOException e) {
                e.printStackTrace();
            }
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
    private void addToBeanDefinitionMap(Class<?> clazz) {
        String scope = "singleton";

        if (clazz.getDeclaredAnnotation(Scope.class) != null) {
            String value = clazz.getDeclaredAnnotation(Scope.class).value();
            if (value.equals("prototype")) {
                scope = value;
            }
        }

        beanDefinitionMap.put(clazz.getDeclaredAnnotation(Component.class).value(), new BeanDefinition(scope, clazz));
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
     * 判断Bean的类型
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
                return createBean(definition);
            }
        } else {
            throw new NullPointerException();
        }
    }

    /**
     * 创建原型Bean
     *
     * @param definition
     * @return
     */
    private Object createBean(BeanDefinition definition) {
        Class clazz = definition.getClazz();

        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();
            return instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }

}
