package com.yuuy.spring.framework;

public class BeanDefinition {

    private String scope;

    private Class clazz;

    public BeanDefinition(String scope, Class clazz) {
        this.scope = scope;
        this.clazz = clazz;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
