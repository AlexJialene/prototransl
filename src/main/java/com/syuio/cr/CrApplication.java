package com.syuio.cr;

import com.syuio.cr.loader.CrLoader;
import com.syuio.kits.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/2
 * Time: 17:54
 */
public class CrApplication implements Cr {
    private static final Logger LOGGER = LoggerFactory.getLogger(CrApplication.class);
    private final Map<String, BeanDetermine> map = Collections.synchronizedMap(new HashMap<>(12));

    public CrApplication() {
    }

    @Override
    public void load(CrLoader var1) {
        var1.load(this);
    }

    @Override
    public void addBean(Object var1) {
        Assert.notNull(var1);
        this.addBean(var1.getClass().getName(), var1);
    }

    @Override
    public void addBean(String var1, Object var2) {
        Assert.notNull(var2);
        //TODO addBean
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
        return null;
    }

    @Override
    public List<BeanDetermine> getBeanDefines() {
        return new ArrayList<>(this.map.values());
    }

    @Override
    public List<Object> getBeans() {

        return null;
    }

    @Override
    public Set<String> getBeanNames() {
        return this.map.keySet();
    }

    @Override
    public void remove(Class<?> var1) {

    }

    @Override
    public void remove(String var1) {

    }

    @Override
    public void clearAll() {

    }
}
