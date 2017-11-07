package com.syuio.itinerary;

import com.syuio.kits.ClassInfo;
import com.syuio.kits.VolumeKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/7
 * Time: 16:11
 */
public class ItineraryBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItineraryBuilder.class);
    private Collection<ClassInfo> serviceClass;
    private ItineraryMappers mappers;

    public ItineraryBuilder(Collection<ClassInfo> service) {
        this.serviceClass = service;
        mappers = new ItineraryMappers();
    }

    public ItineraryMappers build(){
        if (VolumeKit.isNotEmpty(serviceClass)) {
            serviceClass.stream().forEach(classInfo -> {
                mappers.distributeItinerary(classInfo.getClazz());

            });

        }
        return mappers;
    }

}
