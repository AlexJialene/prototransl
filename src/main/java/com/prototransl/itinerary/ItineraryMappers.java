package com.prototransl.itinerary;

import com.prototransl.Transl;
import com.prototransl.cr.Cr;
import com.prototransl.itinerary.loader.ItineraryAnnotationLoader;
import com.prototransl.itinerary.loader.ItineraryLoader;
import com.prototransl.kits.Assert;
import com.prototransl.kits.Reflex;
import com.prototransl.kits.VolumeKit;
import com.prototransl.proto.handle.ProtocolReflex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
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
    private Cr ioc;
    private Map<Integer, Itinerary> methodPool = VolumeKit.newConcurrentHashMap(16);

    public ItineraryMappers() {
        loader = new ItineraryAnnotationLoader();
        ioc = Transl.transl().getIoc();
    }

    @Override
    public void reflex(Integer var1, Object var2) {
        reflex(var1, var2, null);
    }

    @Override
    public void reflex(Integer mType, Object clazz, Object... otherParam) {
        Itinerary itinerary = getItinerary(mType);
        if (null == itinerary) {
            LOGGER.warn("[Warn] - Service method not found mType => {}", mType);
            return;
        }
        Method method = itinerary.getAction();
        Object obj = this.ioc.getBean(itinerary.getBeLongBeanName());
        if (VolumeKit.isEmpty(otherParam)) {
            Reflex.invoke(method, obj, clazz);
        } else {
            if (0 < itinerary.getOtherParamLen() && otherParam.length == itinerary.getOtherParamLen()) {
                Reflex.invoke(method , obj , VolumeKit.concat(new Object[]{clazz} ,otherParam));
            }else {
                LOGGER.error("[error] - cannot invoke action [{}] , please check it");
            }
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

    protected Itinerary getItinerary(Integer var1) {
        return methodPool.get(var1);
    }

}
