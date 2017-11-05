package com.syuio.method;

import com.syuio.kits.CollectionConfigure;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/4
 * Time: 15:59
 */
public class ProtocolMethod {
    private String beLongBeanName;
    private Method action;
    private String actionName;
    private Integer mType;
    private Map<String, String> param = CollectionConfigure.newHashMap();

    public ProtocolMethod() {
    }

    public ProtocolMethod(Method action, String actionName , String beLongBeanName) {
        this.action = action;
        this.actionName = actionName;
        this.beLongBeanName = beLongBeanName;
    }

    public ProtocolMethod(Method action , String beLongBeanName) {
        this(action , action.getName() , beLongBeanName);
    }

    public ProtocolMethod(Method action, String actionName, Map<String, String> param) {
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
