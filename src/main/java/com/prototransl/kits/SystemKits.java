package com.prototransl.kits;

import com.prototransl.annotation.BasisProtocol;
import com.prototransl.annotation.Itinerary;
import com.prototransl.annotation.Protocol;
import com.prototransl.annotation.ProtocolService;

import java.lang.annotation.Annotation;

public class SystemKits {
    public static final String UNPACK_PROTOCOL_CLASS_NAME = "com.prototransl.proto.pack.app.UnpackProtocol";
    public static final String PACK_PROTOCOL_CLASS_NAME = "com.prototransl.proto.pack.app.PackProtocol";

    public static final Class<Protocol> PROTOCOL_CLASS = Protocol.class;
    public static final Class<ProtocolService> PROTOCOL_SERVICE_CLASS = ProtocolService.class;
    public static final Class<Itinerary> ITINERARY_CLASS = Itinerary.class;
    public static final Class<BasisProtocol> BASIS_PROTOCOL_CLASS = BasisProtocol.class;

}
