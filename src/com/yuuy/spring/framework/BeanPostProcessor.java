package com.yuuy.spring.framework;

/**
 * 实现 Aware 回调的一个接口
 */
public interface BeanPostProcessor {

    Object postProcessorBeforeInitialization(String beanName, Object instance);

    Object postProcessorAfterInitialization(String beanName, Object instance);
}
