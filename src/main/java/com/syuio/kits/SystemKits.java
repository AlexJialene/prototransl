package com.syuio.kits;

import com.syuio.annotation.BasisProtocol;
import com.syuio.annotation.Itinerary;
import com.syuio.annotation.Protocol;
import com.syuio.annotation.ProtocolService;

import java.lang.annotation.Annotation;

public class SystemKits {
    public static final String UNPACK_PROTOCOL_CLASS_NAME = "com.syuio.proto.pack.app.UnpackProtocol";
    public static final String PACK_PROTOCOL_CLASS_NAME = "com.syuio.proto.pack.app.PackProtocol";

    public static final Class<Protocol> PROTOCOL_CLASS = Protocol.class;
    public static final Class<ProtocolService> PROTOCOL_SERVICE_CLASS = ProtocolService.class;
    public static final Class<Itinerary> ITINERARY_CLASS = Itinerary.class;
    public static final Class<BasisProtocol> BASIS_PROTOCOL_CLASS = BasisProtocol.class;

}
