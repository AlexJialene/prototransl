package com.syuio.cr;

import com.syuio.cr.loader.CrLoader;

import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/3
 * Time: 1:04
 */
public interface Cr {

    void load(CrLoader var1);

    void addBean(Object var1);

    void addBean(String var1, Object var2);

    void setBean(Class<?> var1, Object var2);

    Object getBean(String var1);

    List<BeanDetermine> getDetermines();

    List<Object> getBeans();

    Set<String> getBeanNames();

    void remove(Class<?> var1);

    void remove(String var1);

    void clearAll();
}
