package com.prototransl.proto;

import java.lang.reflect.Field;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/8
 * Time: 15:34
 */
public class ProtocolField {
    private Field field;
    private Class<?> type;
    private boolean isUintField;

    public ProtocolField(Field field) {
        this(field, field.getType());
    }

    public ProtocolField(Field field, Class<?> type) {
        this.field = field;
        this.type = type;
        this.isUintField = "com.prototransl.proto.pack.entity".equals(type.getPackage().getName());
    }

    public ProtocolField(Field field, Class<?> type, boolean isUintField) {
        this.field = field;
        this.type = type;
        this.isUintField = isUintField;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public boolean isUintField() {
        return isUintField;
    }

    public void setUintField(boolean uintField) {
        isUintField = uintField;
    }
}
