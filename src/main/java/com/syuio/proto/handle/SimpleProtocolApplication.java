package com.syuio.proto.handle;

import com.syuio.annotation.Protocol;
import com.syuio.kits.Assert;
import com.syuio.proto.ProtoApplication;
import com.syuio.proto.pack.ProtoBuffer;
import com.syuio.proto.pack.adapter.Pack;
import com.syuio.proto.pack.adapter.UnpackExecute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/6
 * Time: 11:08
 */
public class SimpleProtocolApplication implements ProtocolApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleProtocolApplication.class);
    private ProtoApplication application;
    private ProtocolReflex reflex;

    public SimpleProtocolApplication(ProtoApplication protoApplication, ProtocolReflex reflex) {
        this.application = protoApplication;
        this.reflex = reflex;
    }

    public void setReflex(ProtocolReflex reflex) {
        this.reflex = reflex;
    }

    @Override
    public void receive(ProtoBuffer buffer) {
        if (!checkProtocol(buffer))
            return;
        Integer mType = getProtocolType(buffer);
        Object obj = application.getProtocolUnpackBean(mType, buffer);
        LOGGER.info("receive message - mType => {}", mType);
        Assert.notNull(mType,"[error] - Parsing protocol error: cannot parsing protocol's mType value");
        Assert.notNull(obj,"[error] - Parsing protocol error: cannot parsing protocol's body");
        buffer.setmProtolLen(-1);
        reflex.reflex(mType , obj);
    }

    @Override
    public byte[] pack(Object var1) {
        return application.pack(var1);
    }

    private Integer getProtocolType(ProtoBuffer buffer) {
        byte[] tmpBuff = buffer.takeBuff(2);
        UnpackExecute unpack = new UnpackExecute(tmpBuff);
        return unpack.popUint16().intValue();
    }

    private boolean checkProtocol(ProtoBuffer buffer) {
        int len = buffer.getmProtolLen();
        if (0 >= len) {
            if (2 > buffer.len()) {
                return false;
            }
            byte[] lenBuff = buffer.takeBuff(2);
            UnpackExecute unpackExecute = new UnpackExecute(lenBuff);
            //Protocol type does not require type of content,so remove two len
            buffer.setmProtolLen(unpackExecute.popUint16().intValue()-2);
        }
        if (buffer.len() >= buffer.getmProtolLen())
            return true;
        return false;
    }
}
