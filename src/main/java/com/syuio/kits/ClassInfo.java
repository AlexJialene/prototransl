package com.syuio.kits;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/3
 * Time: 0:49
 */
public class ClassInfo {
    private String className;
    private Class<?> clazz;

    public ClassInfo(String className) {
        this.className = className;
    }

    public ClassInfo(String className, Class<?> clazz) {
        this.className = className;
        this.clazz = clazz;
    }

    public String getClassName() {
        return className;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Object newInstance(){
        try{
            return this.clazz.newInstance();
        }catch (Exception var1){
            throw new RuntimeException();
        }
    }

}
