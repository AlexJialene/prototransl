package com.syuio.itinerary.loader;

import com.syuio.itinerary.Itinerary;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/4
 * Time: 16:02
 */
public interface ItineraryLoader {

    List<Itinerary> load(Class<?> clazz);
}
