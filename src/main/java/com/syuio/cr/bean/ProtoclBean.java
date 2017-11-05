package com.syuio.cr.bean;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/5
 * Time: 18:43
 */
public class ProtoclBean extends BasisBean {
    private Integer mType;

    public ProtoclBean(Class<?> clazz, Integer mType) {
        super(clazz);
        this.mType = mType;
    }

    public Integer getmType() {
        return mType;
    }

    public void setmType(Integer mType) {
        this.mType = mType;
    }
}
