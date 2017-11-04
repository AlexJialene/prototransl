package com.syuio.cr.loader;

import com.syuio.Syuio;
import com.syuio.config.Config;
import com.syuio.config.Configuration;
import com.syuio.cr.Cr;
import com.syuio.cr.CrApplication;
import com.syuio.kits.Assert;
import com.syuio.kits.ClassInfo;
import com.syuio.kits.CollectionConfigure;
import com.syuio.method.loader.MethodAnnotationLoader;
import com.syuio.method.loader.ProtocolMethodLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.function.Function;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/2
 * Time: 23:53
 */
public final class CrAnnotationLoader implements CrLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(CrAnnotationLoader.class);
    private Syuio syuio =Syuio.syuio();
    private ProtocolMethodLoader protocolMethodLoader = new MethodAnnotationLoader();

    public CrAnnotationLoader() {
    }

    @Override
    public void load(Cr cr) {
        if (!syuio.isInit()){
            Configuration configuration = syuio.getConfig();
            String protoclServicePackage = configuration.getProperties(Configuration.SyuioEnv.PROTOCOL_PACKAGE);
            String servicePackage = configuration.getProperties(Configuration.SyuioEnv.SERVICE_PACKAGE);
            Assert.notNull(protoclServicePackage , "[init failed] - syuio.protocol.servicePackage must not be null");



        }
    }


    private Collection<ClassInfo> find(String packageName , Annotation annotation , boolean recursive){
        Collection<ClassInfo> classs = CollectionConfigure.newArrayList();

        return null;
    }
}
