package com.syuio.itinerary.loader;

import com.syuio.itinerary.Itinerary;
import com.syuio.itinerary.ItineraryMappers;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/4
 * Time: 16:02
 */
public interface ItineraryLoader {

    Map<Integer , Itinerary> load(Class<?> clazz);

}
