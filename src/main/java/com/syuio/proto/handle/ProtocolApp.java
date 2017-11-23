package com.syuio.proto.handle;

import com.syuio.proto.pack.ProtoBuffer;
import com.syuio.proto.pack.adapter.Pack;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/6
 * Time: 11:00
 */
public interface ProtocolApp {

    void receive(ProtoBuffer buffer);

    void pack(Object var1);
}
