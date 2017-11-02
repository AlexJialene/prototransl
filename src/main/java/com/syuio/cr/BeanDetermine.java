package com.syuio.cr;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/3
 * Time: 0:57
 * 扫描到的类容器
 */
public class BeanDetermine {
    private Object bean;
    private Class<?> clazz;

    public BeanDetermine(Object bean) {
        this(bean, bean.getClass());
    }

    public BeanDetermine(Object bean, Class<?> clazz) {
        this.bean = bean;
        this.clazz = clazz;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Class<?> getType() {
        return clazz;
    }

    public void setType(Class<?> clazz) {
        this.clazz = clazz;
    }
}
