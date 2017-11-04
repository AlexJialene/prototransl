package com.syuio.method;

import com.syuio.kits.CollectionConfigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/4
 * Time: 16:06
 */
public class Methods {
    private static final Logger LOGGER = LoggerFactory.getLogger(Methods.class);
    private Map<Class<?>,ProtocolMethod[]> classMethodMap = CollectionConfigure.newConcurrentHashMap(16);

}
