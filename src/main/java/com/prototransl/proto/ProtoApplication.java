package com.prototransl.proto;

import com.prototransl.Transl;
import com.prototransl.annotation.Protocol;
import com.prototransl.cr.Cr;
import com.prototransl.kits.*;
import com.prototransl.proto.handle.ProtocolReflex;
import com.prototransl.proto.handle.SimpleProtocolApplication;
import com.prototransl.proto.pack.ProtoBuffer;
import com.prototransl.proto.pack.adapter.Pack;
import com.prototransl.proto.pack.adapter.PackExecute;
import com.prototransl.proto.pack.adapter.Unpack;
import com.prototransl.proto.pack.adapter.UnpackExecute;
import com.prototransl.proto.pack.entity.Uint16;
import com.prototransl.proto.pack.entity.Uint32;
import com.prototransl.proto.pack.entity.Uint64;
import com.prototransl.proto.pack.entity.Uint8;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
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
    private Integer packCap;
    private ByteOrder byteOrder;
    private boolean isInit = false;

    public ProtoApplication(Cr cr, Integer packCap, ByteOrder byteOrder) {
        this.ioc = cr;
        this.packCap = packCap;
        this.byteOrder = byteOrder;
    }

    public void initProtocol(Collection<ClassInfo> protocol, ProtocolReflex reflex) {
        if (!this.isInit) {
            if (VolumeKit.isNotEmpty(protocol)) {
                protocol.stream().forEach(classInfo -> {
                    Protocol p = classInfo.getClazz().getAnnotation(SystemKits.PROTOCOL_CLASS);
                    if (null != p) {
                        put(p.mType(), classInfo.getClassName());
                        buildField(p.mType(), classInfo.getClazz());
                        this.ioc.addBean(classInfo.getClazz(), Target.PROTOCOL);
                    }
                });
                Transl.transl().setProtocolApp(new SimpleProtocolApplication(this, reflex));
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
        if (!isImplentUnpackAndPackApp(clazz, SystemKits.UNPACK_PROTOCOL_CLASS_NAME)) {
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
            //Realize the com.prototransl.proto.pack.app package class
            byte[] var1 = buffer.takeBuff(buffer.getmProtolLen());
            Unpack unpack = new UnpackExecute(var1);
            Method method = Reflex.getMethod(clazz, "unpackProto", Unpack.class);
            Reflex.invoke(method, obj, unpack);
        } else {
            byte[] var2 = buffer.takeBuff(buffer.getmProtolLen());
            Unpack unpack = new UnpackExecute(var2);
            ProtocolField[] protocolFields = getprotocolFields(mType);
            Arrays.stream(protocolFields).forEach(field -> assemblyField(obj, field, unpack));
            return obj;
        }
        return null;
    }

    private void assemblyField(Object obj, ProtocolField field, Unpack unpack) {
        try {
            Field var1 = field.getField();
            var1.setAccessible(true);
            switch (field.getType().getName()) {
                case "java.lang.String":
                    var1.set(obj, unpack.popString());
                    break;
                case "java.lang.Boolean":
                case "boolean":
                    var1.set(obj , unpack.popBoolean());
                case "int":
                case "java.lang.Integer":
                    var1.set(obj, unpack.popUint32().intValue());
                    break;
                case "long":
                case "java.lang.Long":
                    var1.set(obj, unpack.popUint64().longValue());
                    break;
                case "short":
                case "java.lang.Short":
                    var1.set(obj, unpack.popUint16().shortValue());
                    break;
                case "com.prototransl.proto.pack.entity.Uint8":
                    var1.set(obj, unpack.popUint8());
                    break;
                case "com.prototransl.proto.pack.entity.Uint32":
                    var1.set(obj, unpack.popUint32());
                    break;
                case "com.prototransl.proto.pack.entity.Uint16":
                    var1.set(obj, unpack.popUint16());
                    break;
                case "com.prototransl.proto.pack.entity.Uint64":
                    var1.set(obj, unpack.popUint64());
                    break;
            }
        } catch (IllegalAccessException e) {
            LOGGER.error("[error] - Field UnpackProto assembly failed", e);
        }
    }

    public ProtocolField[] getprotocolFields(Integer mType) {
        return fieldMap.get(mType);
    }

    public byte[] pack(Object var1) {
        Assert.notNull(var1, "[error] - the Object must be not null");
        Class clazz = var1.getClass();
        Protocol protocol = (Protocol) clazz.getAnnotation(SystemKits.PROTOCOL_CLASS);
        Assert.notNull(protocol, "[error] - the Class " + clazz.getName() + " must be Protocol type");
        Integer mType = protocol.mType();
        Pack pack = new PackExecute(this.packCap, this.byteOrder);
        pack.push(Uint16.toUInt(mType));
        if (isImplentUnpackAndPackApp(clazz, SystemKits.PACK_PROTOCOL_CLASS_NAME)) {
            Method method = Reflex.getMethod(clazz, "packProto", Pack.class);
            Reflex.invoke(method, var1, pack);
            return pack2Byte(pack);
        } else {
            Field[] fields = clazz.getDeclaredFields();
            if (!VolumeKit.isEmpty(fields)) {
                Arrays.stream(fields).forEach(field -> {
                    field.setAccessible(true);
                    try {
                        switch (field.getType().getName()) {
                            case "java.lang.String":
                                pack.push(field.get(var1).toString());
                                break;
                            case "java.lang.Boolean":
                            case "boolean":
                                pack.push(Uint8.toUInt((Boolean)field.get(var1)));
                            case "int":
                            case "java.lang.Integer":
                                pack.push(Uint32.toUInt((Integer) field.get(var1)));
                                break;
                            case "long":
                            case "java.lang.Long":
                                pack.push(Uint64.toUInt((Long) field.get(var1)));
                                break;
                            case "short":
                            case "java.lang.Short":
                                pack.push(Uint16.toUInt((Short) field.get(var1)));
                                break;
                            case "com.prototransl.proto.pack.entity.Uint8":
                                pack.push(((Uint8) field.get(var1)));
                                break;
                            case "com.prototransl.proto.pack.entity.Uint32":
                                pack.push((Uint32) field.get(var1));
                                break;
                            case "com.prototransl.proto.pack.entity.Uint16":
                                pack.push((Uint16) field.get(var1));
                                break;
                            case "com.prototransl.proto.pack.entity.Uint64":
                                pack.push((Uint64) field.get(var1));
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                return pack2Byte(pack);
            }
            return null;
        }
    }

    private boolean isImplentUnpackAndPackApp(Class<?> cla, String className) {
        Class[] clz = cla.getInterfaces();
        if (!VolumeKit.isEmpty(clz)) {
            long var1 = Arrays.stream(clz)
                    .filter(clazz -> className.equals(clazz.getName()))
                    .count();
            if (1 == var1)
                return true;
        }

        return false;
    }

    private byte[] pack2Byte(Pack pack) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(pack.size() + 2);
        byteBuffer.order(this.byteOrder);
        byteBuffer.putShort((short) pack.size());
        byteBuffer.put(pack.toBytes(), 0, pack.size());
        byteBuffer.flip();
        return byteBuffer.array();
    }


}
