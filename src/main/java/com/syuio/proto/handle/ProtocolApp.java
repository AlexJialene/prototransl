package com.syuio.proto.handle;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/6
 * Time: 11:00
 */
public interface ProtocolApp {

    void receive(byte[] message);
}
