package com.prototransl.itinerary.loader;

import com.prototransl.itinerary.Itinerary;
import com.prototransl.itinerary.ItineraryMappers;
import com.prototransl.kits.Assert;
import com.prototransl.kits.SystemKits;
import com.prototransl.kits.VolumeKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/4
 * Time: 16:04
 */
public class ItineraryAnnotationLoader implements ItineraryLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItineraryMappers.class);

    @Override
    public Map<Integer, Itinerary> load(Class<?> clazz) {
        Assert.notNull(clazz);
        Method[] methods = clazz.getMethods();
        Map<Integer, Itinerary> map = VolumeKit.newHashMap();
        if (!VolumeKit.isEmpty(methods)) {
            for (Method method : methods) {
                com.prototransl.annotation.Itinerary anno = method.getAnnotation(SystemKits.ITINERARY_CLASS);
                if (null != anno) {
                    map.put(anno.mType(), getItinerary(method, clazz.getName()));
                    LOGGER.info("add Itinerary => [ {}.{} mType={} ]  ", clazz.getName(), method.getName(), anno.mType());
                }
            }
        }
        return map;
    }

    public Itinerary getItinerary(Method method, String beanName) {
        Class[] clazz = method.getParameterTypes();
        if (!VolumeKit.isEmpty(clazz)) {
            if (null != clazz[0].getAnnotation(SystemKits.PROTOCOL_CLASS)) {
                Itinerary itinerary = new Itinerary(method, method.getName(), beanName);
                if (1 < clazz.length) {
                    itinerary.setOtherParamLen(clazz.length);
                }
                return itinerary;
            }
            LOGGER.error("[error] - The action parameter type [ {}.{}({}) ] Does not meet the verification", beanName, method.getName(), clazz[0].getName());
        }
        LOGGER.error("[error] - The action does not meet the verification", new IllegalAccessException());
        return null;
    }
}
