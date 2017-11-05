package com.syuio.cr;

import com.syuio.kits.Target;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/3
 * Time: 0:57
 */
public class BeanDetermine {
    private Object bean;
    private Class<?> clazz;
    private Target target;
    private Integer mType;
    protected boolean isSignle;

    public BeanDetermine(Object bean) {
        this(bean, bean.getClass());
    }

    public BeanDetermine(Object bean, Class<?> clazz) {
        this(bean, clazz, Target.PROTOCOL);

    }

    public BeanDetermine(Object bean, Class<?> clazz, Target target) {
        this.bean = bean;
        this.clazz = clazz;
        this.target = target;
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

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Target getTarget() {
        return target;
    }
}
