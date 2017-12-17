package com.prototransl.itinerary.loader;

import com.prototransl.itinerary.Itinerary;
import com.prototransl.itinerary.ItineraryMappers;

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
