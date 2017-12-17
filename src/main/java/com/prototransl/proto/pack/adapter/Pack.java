package com.prototransl.proto.pack.adapter;

import com.prototransl.proto.pack.entity.Uint16;
import com.prototransl.proto.pack.entity.Uint32;
import com.prototransl.proto.pack.entity.Uint64;
import com.prototransl.proto.pack.entity.Uint8;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/8
 * Time: 16:28
 */
public interface Pack {

    int size();

    Pack push(byte val);

    Pack push(Uint8 val);

    Pack push(Uint16 val);

    Pack push(Uint32 val);

    Pack push(Uint64 val);

    Pack push(String val);

    Pack push(boolean val);

    byte[] toBytes();

    Pack push(byte[] val);

}
