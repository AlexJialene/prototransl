package com.syuio.itinerary.loader;

import com.syuio.cr.Cr;
import com.syuio.itinerary.Itinerary;
import com.syuio.itinerary.ItineraryMappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/4
 * Time: 16:04
 */
public class ItineraryAnnotationLoader implements ItineraryLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItineraryMappers.class);
    private Cr ioc;

    public ItineraryAnnotationLoader(Cr cr) {
        this.ioc = cr;
    }

    @Override
    public List<Itinerary> load(Class<?> clazz) {
        return null;
    }
}
