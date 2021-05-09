package com.yuuy.spring.app.services;

import com.yuuy.spring.framework.BeanPostProcessor;
import com.yuuy.spring.framework.Component;

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessorBeforeInitialization(String beanName, Object instance) {
        System.out.println("初始化前");
        if (beanName.equals("userService")) {
            ((UserService) instance).setMessage("a message in userService");
        }
        return instance;
    }

    @Override
    public Object postProcessorAfterInitialization(String beanName, Object instance) {
        System.out.println("初始化后");
        return instance;
    }
}
