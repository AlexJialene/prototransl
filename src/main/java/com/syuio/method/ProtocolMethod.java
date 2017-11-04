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
    private Method action;
    private String actionName;
    private Map<String, String> param = CollectionConfigure.newHashMap();

    public ProtocolMethod() {
    }

    public ProtocolMethod(Method action, String actionName) {
        this.action = action;
        this.actionName = actionName;
    }

    public ProtocolMethod(Method action) {
        this.action = action;
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
}
