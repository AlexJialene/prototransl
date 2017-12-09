package com.syuio.kits;

public enum SystemEnumKits {
    UNPACK_PROTOCOL_CLASS_NAME("com.syuio.proto.pack.app.UnpackProtocol"),
    PACK_PROTOCOL_CLASS_NAME("com.syuio.proto.pack.app.PackProtocol");


    public String key;
    SystemEnumKits(String key) {
        this.key = key;
    }
}
