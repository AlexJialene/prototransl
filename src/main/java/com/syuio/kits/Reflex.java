package com.syuio.kits;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/5
 * Time: 14:47
 */
public class Reflex {

    public static Collection<?> findInternalClassByAnno(Collection<ClassInfo> collection, Class<? extends Annotation> anno) {
        if (VolumeKit.isNotEmpty(collection)) {
            return collection.stream()
                    .flatMap(classInfo -> Arrays.stream(classInfo.getClazz().getDeclaredClasses())
                            .filter(aClass -> null != aClass.getAnnotation(anno)))
                    .collect(Collectors.toCollection(HashSet::new));
        }
        return null;
    }

    public static Object newInstance(Class<?> clazz) {
        Assert.notNull(clazz);
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
