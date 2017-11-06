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

    public static void notBlank(CharSequence str) {
        if(str == null) {
            throw new NullPointerException("[failed] - The validated string is blank");
        } else if(0 == str.toString().trim().length()) {
            throw new IllegalArgumentException("[failed] - The validated string is blank");
        }
    }

    public static void trueException(boolean ex , String message, Object...value){
        if (ex){
            throw new IllegalArgumentException(String.format(message, value));
        }
    }

}
