package com.prototransl.cr;

import com.prototransl.kits.Target;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/3
 * Time: 0:57
 */
public class BeanDetermine {
    private boolean isSignle;
    private Object bean;
    private Class<?> clazz;

    public BeanDetermine(Object bean) {
        this(bean, bean.getClass());
    }

    public BeanDetermine(Object bean, Class<?> clazz) {
        this(bean, clazz, true);
    }

    public BeanDetermine(Object bean, Class<?> clazz, boolean isSignle) {
        this.bean = bean;
        this.clazz = clazz;
        this.isSignle = isSignle;

    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public boolean isSignle() {
        return isSignle;
    }

    public void setSignle(boolean signle) {
        isSignle = signle;
    }
}
