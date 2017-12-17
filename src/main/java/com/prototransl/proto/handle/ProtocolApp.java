package com.prototransl.proto.handle;

import com.prototransl.proto.pack.ProtoBuffer;
import com.prototransl.proto.pack.adapter.Pack;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/6
 * Time: 11:00
 */
public interface ProtocolApp {

    void receive(ProtoBuffer buffer);

    void receive(ProtoBuffer buffer , Object ... obj);

    byte[] pack(Object var1);
}
