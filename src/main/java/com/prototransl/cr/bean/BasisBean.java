package com.prototransl.cr.bean;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/5
 * Time: 18:38
 */
public class BasisBean {
    protected String beanName;
    protected Class<?> clazz;
    protected boolean isSignle;

    public BasisBean(Class<?> clazz) {
        this(clazz, true);
    }

    public BasisBean(Class<?> clazz, boolean isSignle) {
        this(clazz.getName(), clazz, isSignle);
    }

    public BasisBean(String beanName, Class<?> clazz, boolean isSignle) {
        this.beanName = beanName;
        this.clazz = clazz;
        this.isSignle = isSignle;
    }
}
