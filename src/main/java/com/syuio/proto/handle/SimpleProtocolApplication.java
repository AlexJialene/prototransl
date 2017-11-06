package com.syuio.proto.handle;

import com.syuio.proto.ProtoApplication;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/6
 * Time: 11:08
 */
public class SimpleProtocolApplication implements ProtocolApp{
    private ProtoApplication application;
    private ProtocolReflex reflex;

    public SimpleProtocolApplication(ProtoApplication application) {
        this.application = application;
    }

    public SimpleProtocolApplication(ProtoApplication protoApplication, ProtocolReflex reflex) {
        this.application = protoApplication;
        this.reflex = reflex;
    }

    public void setReflex(ProtocolReflex reflex) {
        this.reflex = reflex;
    }

    @Override
    public void receive(byte[] message) {

    }

}
