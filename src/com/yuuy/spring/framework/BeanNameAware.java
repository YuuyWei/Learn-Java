package com.yuuy.spring.framework;

/**
 * 实现 Aware 回调的一个接口
 */
public interface BeanNameAware {

    void setBeanName(String beanName);
}
