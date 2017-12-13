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
public class PackExecute implements Pack {

    protected ByteBuffer buffer;

    public PackExecute() {
        buffer = ByteBuffer.allocate(512);
        buffer.order(ByteOrder.BIG_ENDIAN);
    }

    public PackExecute(int cap , ByteOrder byteOrder){
        buffer = ByteBuffer.allocate(cap);
        buffer.order(byteOrder);
    }

    public int size() {
        return buffer.position();
    }

    public byte[] toBytes() {
        return buffer.array();
    }

    public Pack push(byte val) {
        autoExpand(1);
        buffer.put(val);

        return this;
    }

    public Pack push(boolean val) {
        return push(Uint8.toUInt(val ? 1 : 0));
    }

    public Pack push(Uint8 val) {
        autoExpand(1);
        buffer.put(val.byteValue());

        return this;
    }

    public Pack push(Uint16 val) {
        autoExpand(2);
        buffer.putShort(val.shortValue());

        return this;
    }

    public Pack push(Uint32 val) {
        autoExpand(4);
        buffer.putInt(val.intValue());

        return this;
    }

    public Pack push(Uint64 val) {
        autoExpand(8);
        buffer.putLong(val.longValue());

        return this;
    }

    public Pack push(String val) {
        try {
            return push(val.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public Pack push(byte[] val) {
        autoExpand(2 + val.length);
        push(Uint16.toUInt(val.length));
        buffer.put(val);

        return this;
    }

    protected void autoExpand(int expectedRemaining) {
        expand(expectedRemaining, true);
    }

    protected void expand(int expectedRemaining, boolean autoExpand) {
        expand(buffer.position(), expectedRemaining, autoExpand);
    }

    private void expand(int pos, int expectedRemaining, boolean autoExpand) {
        int end = pos + expectedRemaining;
        int newCapacity;
        if (autoExpand) {
            newCapacity = normalizeCapacity(end);
        } else {
            newCapacity = end;
        }
        if (newCapacity > buffer.capacity()) {
            capacity(newCapacity);
        }

        if (end > buffer.limit()) {
            buffer.limit(end);
        }
    }

    public void capacity(int newCapacity) {

        if (newCapacity > buffer.capacity()) {
            int pos = buffer.position();
            int limit = buffer.limit();
            ByteOrder bo = buffer.order();

            ByteBuffer oldBuf = buffer;
            ByteBuffer newBuf = ByteBuffer.allocate(newCapacity);
            oldBuf.clear();
            newBuf.put(oldBuf);
            buffer = newBuf;

            buffer.limit(limit);
            buffer.position(pos);
            buffer.order(bo);
        }
    }

    protected static int normalizeCapacity(int requestedCapacity) {
        if (requestedCapacity < 0) {
            return Integer.MAX_VALUE;
        }

        int newCapacity = Integer.highestOneBit(requestedCapacity);
        newCapacity <<= (newCapacity < requestedCapacity ? 1 : 0);
        return newCapacity < 0 ? Integer.MAX_VALUE : newCapacity;
    }
}
