package com.syuio.proto;

import com.syuio.Syuio;
import com.syuio.annotation.Protocol;
import com.syuio.cr.Cr;
import com.syuio.kits.*;
import com.syuio.proto.handle.ProtocolReflex;
import com.syuio.proto.handle.SimpleProtocolApplication;
import com.syuio.proto.pack.ProtoBuffer;
import com.syuio.proto.pack.adapter.Unpack;
import com.syuio.proto.pack.adapter.UnpackExecute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/5
 * Time: 19:12
 */
public class ProtoApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProtoApplication.class);
    private final Map<Integer, String> proto = VolumeKit.newConcurrentHashMap(32);
    private final Map<Integer, ProtocolField[]> fieldMap = VolumeKit.newConcurrentHashMap(32);
    //private final Map<Integer, Class<?>> classMap = VolumeKit.newConcurrentHashMap(32);
    private Cr ioc;
    private boolean isInit = false;

    public ProtoApplication(Cr cr) {
        this.ioc = cr;
    }

    public void initProtocol(Collection<ClassInfo> protocol, ProtocolReflex reflex) {
        if (!this.isInit) {
            if (VolumeKit.isNotEmpty(protocol)) {
                protocol.stream().forEach(classInfo -> {
                    Protocol p = classInfo.getClazz().getAnnotation(Protocol.class);
                    if (null != p) {
                        put(p.mType(), classInfo.getClassName());
                        buildField(p.mType(), classInfo.getClazz());
                        this.ioc.addBean(classInfo.getClazz(), Target.PROTOCOL);
                    }
                });
                Syuio.syuio().setProtocolApp(new SimpleProtocolApplication(this, reflex));
                this.isInit = true;
            }
        }
    }

    private void put(Integer mType, String className) {
        this.proto.put(mType, className);
    }

    public String getBeanName(Integer mType) {
        return proto.get(mType);
    }

    public Object getProtocolBean(Integer mType) {
        String beanName = getBeanName(mType);
        if (StringUtils.isNotEmpty(beanName) && StringUtils.isNotBlank(beanName)) {
            return ioc.getBean(beanName);
        }
        return null;
    }

    public Class<?> getProtocolClass(Integer mType) {
        String beanName = getBeanName(mType);
        if (StringUtils.isNotEmpty(beanName) && StringUtils.isNotBlank(beanName)) {
            return ioc.getBeanClass(beanName);
        }
        return null;
    }


    private void buildField(Integer mType, Class<?> clazz) {
        Assert.notNull(clazz);
        if (0 == clazz.getInterfaces().length) {
            Field[] fields = clazz.getDeclaredFields();
            if (!VolumeKit.isEmpty(fields)) {
                ProtocolField[] protocolFields = new ProtocolField[fields.length];
                for (int i = 0; i < fields.length; i++) {
                    protocolFields[i] = new ProtocolField(fields[i]);
                }
                this.fieldMap.put(mType, protocolFields);
            }
        }
    }

    public Object getProtocolUnpackBean(Integer mType, ProtoBuffer buffer) {
        Assert.notNull(mType);
        Object obj = getProtocolBean(mType);
        Class clazz = getProtocolClass(mType);
        Assert.notNull(obj, "[error] - Container error: Cannot get belong mType's Object");
        if (!fieldMap.containsKey(mType)) {
            //Realize the com.syuio.proto.pack.app package class
            //handle
            byte[] var1 = buffer.takeBuff(buffer.getmProtolLen());
            UnpackExecute unpackExecute = new UnpackExecute(var1);
            try {
                Method method = clazz.getMethod("unpackProto", Unpack.class);
                method.invoke(obj, unpackExecute);
                return obj;
            } catch (NoSuchMethodException e) {
                LOGGER.error("[error] - method unpackProto not found", e);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            ProtocolField[] protocolFields = getprotocolFields(mType);

            return null;
        }
        return null;
    }

    public ProtocolField[] getprotocolFields(Integer mType) {
        return fieldMap.get(mType);
    }
}
