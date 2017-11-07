package model;

import com.syuio.annotation.Itinerary;
import com.syuio.itinerary.loader.ItineraryAnnotationLoader;
import com.syuio.itinerary.loader.ItineraryLoader;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/7
 * Time: 15:35
 */
@com.syuio.annotation.ProtocolService
public class ProtocolService {



    @Itinerary(mType=1)
    public void proto1(User user){

    }

    public static void main(String[] args) {
        ItineraryLoader itineraryLoader = new ItineraryAnnotationLoader();
        itineraryLoader.load(ProtocolService.class);

    }

}
