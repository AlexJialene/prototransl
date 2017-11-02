package com.syuio.kits;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/3
 * Time: 1:10
 */
public class Assert {

    public static void notNull(Object object){
        notNull(object , "[failed] - this Object is required; it must not be null");
    }

    public static void notNull(Object object,String message){
        if (null == object)
            throw new NullPointerException(message);
    }

}
