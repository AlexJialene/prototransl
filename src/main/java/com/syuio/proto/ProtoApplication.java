package com.syuio.proto;

import com.syuio.Syuio;
import com.syuio.annotation.Protocol;
import com.syuio.cr.Cr;
import com.syuio.kits.ClassInfo;
import com.syuio.kits.VolumeKit;
import com.syuio.kits.StringUtils;
import com.syuio.kits.Target;
import com.syuio.proto.handle.ProtocolReflex;
import com.syuio.proto.handle.SimpleProtocolApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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


}
