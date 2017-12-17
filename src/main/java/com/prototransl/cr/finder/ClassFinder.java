package com.prototransl.cr.finder;

import com.prototransl.kits.ClassInfo;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/4
 * Time: 17:52
 */
public interface ClassFinder {

    /*
    * 可扩展根据不同来获取，需加接口且扩展实现类中getClassByPackagePath方法
    */

    Set<ClassInfo> getClass(String packageName, Class<? extends Annotation> anno, boolean recursive);

    Set<ClassInfo> getClass(String packageName, Class<?> clazz, Class<? extends Annotation> anno, boolean recursive);


}
