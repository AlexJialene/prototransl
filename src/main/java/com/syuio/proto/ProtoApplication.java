package com.syuio.proto;

import com.syuio.cr.Cr;
import com.syuio.kits.ClassInfo;
import com.syuio.kits.CollectionConfigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/5
 * Time: 19:12
 */
public class ProtoApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProtoApplication.class);
    private final Map<Integer,String> proto = CollectionConfigure.newConcurrentHashMap(32);
    private Cr ioc ;
    private boolean isInit = false;
    public ProtoApplication(Cr cr) {
        this.ioc = cr;
    }


    public void initProtocol(Collection<ClassInfo> protocol){
        if (CollectionConfigure.isNotEmpty(protocol)){

        }

    }


}
