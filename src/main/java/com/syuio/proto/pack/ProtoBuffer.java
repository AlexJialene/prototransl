package com.syuio.proto.pack;

import java.nio.ByteBuffer;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/8
 * Time: 18:09
 */
public class ProtoBuffer {
    //the port
    private static final int MAX_PROTO_LEN = 1024 * 64 * 2;
    private ByteBuffer byteBuffer;
    private int mProtolLen = -1;

    public ProtoBuffer() {
        byteBuffer = ByteBuffer.allocate(MAX_PROTO_LEN);
        byteBuffer.flip();
    }

    public void addBuff(ByteBuffer buffer) {
        int dataLen = buffer.limit() - buffer.position();

        int usedLen = byteBuffer.limit() - byteBuffer.position();
        int freeLen = byteBuffer.capacity() - byteBuffer.limit();
        if (usedLen == 0) {
            byteBuffer.clear();
            byteBuffer.flip();
        }
        else if (freeLen <=  dataLen) {
            byte[] tmp = new byte[usedLen];
            byteBuffer.get(tmp, 0, usedLen);
            byteBuffer.clear();

            byteBuffer.put(tmp);
            byteBuffer.flip();
        }

        int pos = byteBuffer.position();
        int endpos = byteBuffer.limit();
        byteBuffer.position(endpos);
        byteBuffer.limit(byteBuffer.capacity());
        byteBuffer.put(buffer);
        byteBuffer.flip();
        byteBuffer.position(pos);
    }

    public byte[] getBuff(int len) {
        if (byteBuffer.limit() - byteBuffer.position() < len) {
            return null;
        }

        byte[] buffer = new byte[len];
        byteBuffer.get(buffer, 0, len);

        byteBuffer.position(byteBuffer.position() - len);

        return buffer;
    }

    public byte[] takeBuff(int len) {
        if (byteBuffer.limit() - byteBuffer.position() < len) {
            return null;
        }

        byte[] buffer = new byte[len];
        byteBuffer.get(buffer, 0, len);

        return buffer;
    }

    public int len() {
        return byteBuffer.limit() - byteBuffer.position();
    }

    public ByteBuffer buff() {
        return this.byteBuffer;
    }

    public int getmProtolLen() {
        return mProtolLen;
    }

    public void setmProtolLen(int mProtolLen) {
        this.mProtolLen = mProtolLen;
    }
}
