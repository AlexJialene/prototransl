package com.syuio.itinerary;

import com.syuio.kits.VolumeKit;

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
    private Integer mType;
    private Map<String, String> param = VolumeKit.newHashMap();

    public Itinerary() {
    }

    public Itinerary(Method action, String actionName , String beLongBeanName) {
        this.action = action;
        this.actionName = actionName;
        this.beLongBeanName = beLongBeanName;
    }

    public Itinerary(Method action , String beLongBeanName) {
        this(action , action.getName() , beLongBeanName);
    }

    public Itinerary(Method action, String actionName, Map<String, String> param) {
        this.action = action;
        this.actionName = actionName;
        this.param = param;
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

    public Map<String, String> getParam() {
        return param;
    }

    public void setParam(Map<String, String> param) {
        this.param = param;
    }

    public Integer getmType() {
        return mType;
    }

    public void setmType(Integer mType) {
        this.mType = mType;
    }
}
