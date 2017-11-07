package com.syuio.itinerary.loader;

import com.syuio.annotation.Protocol;
import com.syuio.cr.Cr;
import com.syuio.itinerary.Itinerary;
import com.syuio.itinerary.ItineraryMappers;
import com.syuio.kits.Assert;
import com.syuio.kits.VolumeKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/4
 * Time: 16:04
 */
public class ItineraryAnnotationLoader implements ItineraryLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItineraryMappers.class);

    @Override
    public Map<Integer , Itinerary> load(Class<?> clazz) {
        Assert.notNull(clazz);
        Method[] methods = clazz.getMethods();
        Map<Integer , Itinerary> map = VolumeKit.newHashMap();
        if (!VolumeKit.isEmpty(methods)) {
            for (Method method : methods) {
                com.syuio.annotation.Itinerary anno = method.getAnnotation(com.syuio.annotation.Itinerary.class);
                if (null!=anno){
                    map.put(anno.mType() , getItinerary(method , clazz.getName()));
                    LOGGER.info("add Itinerary => [ {}.{} mType={} ]  " , clazz.getName(), method.getName(), anno.mType());
                }
            }
        }
        return map;
    }

    public Itinerary getItinerary(Method method, String beanName) {
        Class[] clazz = method.getParameterTypes();
        if (!VolumeKit.isEmpty(clazz) && 1 == clazz.length) {
            if (null != clazz[0].getAnnotation(Protocol.class)) {
                return new Itinerary(method, method.getName(), beanName);
            }
            LOGGER.error("[error] - The action parameter type [ {}.{}({}) ] Does not meet the verification", beanName, method.getName(), clazz[0].getName());
        } else
            LOGGER.error("[error] - The action [{}.{}] | There are more than one parameter ", beanName, method.getName());
        LOGGER.error("[error] - The action does not meet the verification", new IllegalAccessException());
        return null;
    }
}
