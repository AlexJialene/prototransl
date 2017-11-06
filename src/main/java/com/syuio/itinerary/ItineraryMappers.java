package com.syuio.itinerary;

import com.syuio.Syuio;
import com.syuio.cr.Cr;
import com.syuio.itinerary.loader.ItineraryAnnotationLoader;
import com.syuio.itinerary.loader.ItineraryLoader;
import com.syuio.kits.ClassInfo;
import com.syuio.kits.VolumeKit;
import com.syuio.proto.handle.ProtocolReflex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/4
 * Time: 16:06
 */
public class ItineraryMappers implements ProtocolReflex{
    private static final Logger LOGGER = LoggerFactory.getLogger(ItineraryMappers.class);
    private ItineraryLoader loader;
    private Cr cr = Syuio.syuio().getIoc();
    private boolean isInit = false;

    public ItineraryMappers() {
        loader = new ItineraryAnnotationLoader(this.cr);
    }

    public void distributeItinerary(Collection<ClassInfo> serviceClass){
        if (VolumeKit.isNotEmpty(serviceClass)){
            serviceClass.stream().forEach(classInfo -> {
                List<Itinerary> itineraryList = loader.load(classInfo.getClazz());
                if (VolumeKit.isNotEmpty(itineraryList)){
                    //TODO
                }
            });
        }
    }

    @Override
    public void reflex(Integer mType, Object obj) {
        //TODO
    }
}
