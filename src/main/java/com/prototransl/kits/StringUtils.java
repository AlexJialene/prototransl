package com.prototransl.kits;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/3
 * Time: 11:51
 */
public class StringUtils {

    public static boolean isNotBlank(String key){
        int length;
        if(key != null && (length = key.length()) != 0) {
            for(int i = 0; i < length; ++i) {
                if(!Character.isWhitespace(key.charAt(i))) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }


}
