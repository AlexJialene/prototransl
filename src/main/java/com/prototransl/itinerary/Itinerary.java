package com.prototransl.itinerary;

import com.prototransl.kits.VolumeKit;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/4
 * Time: 15:59
 */
public class Itinerary {
    private String beLongBeanName;
    private Method action;
    private String actionName;
    private Integer otherParamLen;

    public Itinerary() {
    }

    public Itinerary(Method action, String actionName, String beLongBeanName) {
        this.action = action;
        this.actionName = actionName;
        this.beLongBeanName = beLongBeanName;
    }

    public Itinerary(Method action, String beLongBeanName) {
        this(action, action.getName(), beLongBeanName);
    }

    public Method getAction() {
        return action;
    }

    public void setAction(Method action) {
        this.action = action;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getBeLongBeanName() {
        return beLongBeanName;
    }

    public void setBeLongBeanName(String beLongBeanName) {
        this.beLongBeanName = beLongBeanName;
    }

    public Integer getOtherParamLen() {
        return otherParamLen;
    }

    public void setOtherParamLen(Integer otherParamLen) {
        if (1 < otherParamLen)
            this.otherParamLen -= 1;
    }
}
