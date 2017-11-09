package com.syuio.cr;

import com.syuio.cr.loader.CrLoader;
import com.syuio.kits.Assert;
import com.syuio.kits.Reflex;
import com.syuio.kits.VolumeKit;
import com.syuio.kits.Target;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Supplier;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/2
 * Time: 17:54
 */
public final class CrApplication implements Cr {
    private static final Logger LOGGER = LoggerFactory.getLogger(CrApplication.class);
    //The key of container is Class<?>.getName
    private final Map<String, BeanDetermine> map = VolumeKit.newConcurrentHashMap(16);

    public CrApplication() {
    }

    @Override
    public void load(Supplier<CrLoader> var1) {
        var1.get().load(this);
    }

    @Override
    public void addBean(Object var1) {
        Assert.notNull(var1);
        this.addBean(var1.getClass().getName(), var1);
    }

    @Override
    public void addBean(String var1, Object var2) {
        Assert.notNull(var1);
        Assert.notNull(var2);
        BeanDetermine beanDetermine = new BeanDetermine(var2);
        this.map.put(var1, beanDetermine);
    }

    @Override
    public void addBean(Class<?> var2, Target target) {
        Assert.notNull(var2);
        this.addBean(var2.getName(), var2, target == Target.PROTOCOL ? false : true);
    }

    @Override
    public void addBean(Class<?> var1) {
        Assert.notNull(var1);
        this.addBean(var1.getName(), var1, true);
    }

    @Override
    public Class<?> getBeanClass(String var1) {
        Assert.notNull(var1);
        BeanDetermine beanDetermine = this.map.get(var1);
        if (null != beanDetermine)
            return beanDetermine.getClazz();
        return null;
    }

    @Override
    public void setBean(Class<?> var1, Object var2) {
        Assert.notNull(var2);
        BeanDetermine beanDetermine = map.get(var1.getName());
        if (null == beanDetermine)
            beanDetermine = new BeanDetermine(var2, var1);
        else
            beanDetermine.setBean(var2);
        this.map.put(var1.getName(), beanDetermine);
    }

    @Override
    public Object getBean(String var1) {
        BeanDetermine beanDetermine = this.map.get(var1);
        if (null != beanDetermine) {
            if (beanDetermine.isSignle())
                return beanDetermine.getBean();
            else {
                return Reflex.newInstance(beanDetermine.getClazz());
            }

        }
        return null;
    }

    @Override
    public List<BeanDetermine> getDetermines() {
        return new ArrayList<>(this.map.values());
    }

    @Override
    public List<Object> getBeans() {
        Set<String> beanNames = this.getBeanNames();
        List<Object> beans = new ArrayList<>(beanNames.size());
        beanNames.forEach(beanName -> {
            Object bean = this.getBean(beanName);
            if (null != bean)
                beans.add(bean);
        });
        return beans;
    }

    @Override
    public Set<String> getBeanNames() {
        return this.map.keySet();
    }

    @Override
    public void remove(Class<?> var1) {
        this.map.remove(var1.getName());
    }

    @Override
    public void remove(String var1) {
        this.map.remove(var1);
    }

    @Override
    public void clearAll() {
        this.map.clear();
    }

    public void addBean(String var1, Class<?> var2, boolean isSignle) {
        Assert.notNull(var1);
        Assert.notNull(var2);
        Assert.trueException(var2.isInterface(), "Must not be interface: %s", new Object[]{var2.getName()});
        Assert.trueException(Modifier.isAbstract(var2.getModifiers()), "Must not be abstract class: %s", new Object[]{var2.getName()});
        BeanDetermine beanDetermine = getBeanDetermine(var2, isSignle);
        this.map.put(var1, beanDetermine);

    }

    public BeanDetermine getBeanDetermine(Class<?> var1, boolean isSignle) {
        try {
            Object obj = var1.newInstance();
            return new BeanDetermine(obj, var1, isSignle);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;

    }


}
