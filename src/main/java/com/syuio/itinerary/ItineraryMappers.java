package com.syuio.itinerary;

import com.syuio.Syuio;
import com.syuio.cr.Cr;
import com.syuio.itinerary.loader.ItineraryAnnotationLoader;
import com.syuio.itinerary.loader.ItineraryLoader;
import com.syuio.kits.Assert;
import com.syuio.kits.ClassInfo;
import com.syuio.kits.Reflex;
import com.syuio.kits.VolumeKit;
import com.syuio.proto.handle.ProtocolReflex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/4
 * Time: 16:06
 */
public class ItineraryMappers implements ProtocolReflex {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItineraryMappers.class);
    private ItineraryLoader loader;
    private Cr ioc ;
    private Map<Integer, Itinerary> methodPool = VolumeKit.newConcurrentHashMap(16);

    public ItineraryMappers() {
        loader = new ItineraryAnnotationLoader();
        ioc = Syuio.syuio().getIoc();
    }

    @Override
    public void reflex(Integer var1, Object var2) {
        Itinerary itinerary = getItinerary(var1);
        if (null == itinerary){
            LOGGER.warn("[Warn] - Service method not found mType => {}" , var1);
            return ;
        }
        Method method = itinerary.getAction();
        Object obj = this.ioc.getBean(itinerary.getBeLongBeanName());
        try {
            method.invoke(obj , var2);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    protected void distributeItinerary(Class<?> clazz) {
        Assert.notNull(clazz);
        Map<Integer, Itinerary> itineraryMap = loader.load(clazz);
        if (!VolumeKit.isEmpty(itineraryMap)) {
            methodPool.putAll(itineraryMap);
        }
        ioc.addBean(clazz);
    }

    protected Itinerary getItinerary(Integer var1){
        return methodPool.get(var1);
    }

}
