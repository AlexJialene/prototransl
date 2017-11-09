package com.syuio.proto.pack.adapter;

import com.syuio.proto.pack.entity.Uint16;
import com.syuio.proto.pack.entity.Uint32;
import com.syuio.proto.pack.entity.Uint64;
import com.syuio.proto.pack.entity.Uint8;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/8
 * Time: 17:04
 */
public class UnpackExecute implements Unpack {

    protected ByteBuffer buffer;

    public UnpackExecute(byte[] val) {
        this(val, 0, val.length);
    }

    public UnpackExecute(byte[] val, int offset, int length) {
        buffer = ByteBuffer.wrap(val, offset, length);
        buffer.order(ByteOrder.BIG_ENDIAN);
    }

    public boolean popBoolean() {
        return buffer.get() == 1 ? true : false;
    }

    public Uint8 popUint8() {
        return new Uint8(buffer.get());
    }

    public Uint16 popUint16() {
        return new Uint16(buffer.getShort());
    }

    public Uint32 popUint32() {
        return new Uint32(buffer.getInt());
    }

    public Uint64 popUint64() {
        return new Uint64(buffer.getLong());
    }

    public String popString() {
        try {
            byte[] dst = popBytes();
            return new String(dst, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] popBytes() {
        short size = buffer.getShort();
        byte[] dst = new byte[toUnsigned(size)];
        buffer.get(dst);
        return dst;
    }

    protected int toUnsigned(short s) {
        return s & 0xffff;
    }
}
