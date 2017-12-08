package com.syuio.kits;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/5
 * Time: 14:47
 */
public class Reflex {
    private static final Logger LOGGER = LoggerFactory.getLogger(Reflex.class);

    public static Collection<ClassInfo> findInternalClassByAnno(Collection<ClassInfo> collection, Class<? extends Annotation> anno) {
        if (VolumeKit.isNotEmpty(collection)) {
            Collection<Class> clazzs = collection.stream()
                    .flatMap(classInfo -> Arrays.stream(classInfo.getClazz().getDeclaredClasses())
                            .filter(aClass -> null != aClass.getAnnotation(anno)))
                    .collect(Collectors.toCollection(HashSet::new));
            if (VolumeKit.isNotEmpty(clazzs)) {
                Collection<ClassInfo> classInfos = VolumeKit.newHashSet();
                clazzs.forEach(clazz -> {
                    classInfos.add(new ClassInfo(clazz));
                });
                return classInfos;
            }
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

    public static Object invoke(Method var1, Object var2, Object var3) {
        Assert.notNull(var1);
        Assert.notNull(var2);
        Assert.notNull(var3);
        try {
            var1.invoke(var2, var3);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return var2;
    }

    public static Method getMethod(Class<?> var1, String var2, Class<?> var3) {
        try {
            return var1.getMethod(var2, var3);
        } catch (NoSuchMethodException e) {
            LOGGER.error("[error] - method unpackProto not found", e);
        }
        return null;
    }

    public static Method[] getMethods(Class<?> var1) {
        return var1.getMethods();
    }


}
