package com.syuio.cr.loader;

import com.syuio.Syuio;
import com.syuio.annotation.BasisProtocol;
import com.syuio.annotation.Protocol;
import com.syuio.annotation.ProtocolService;
import com.syuio.config.Config;
import com.syuio.config.Configuration;
import com.syuio.cr.Cr;
import com.syuio.cr.CrApplication;
import com.syuio.cr.DynamicFinder;
import com.syuio.cr.finder.ClassFinder;
import com.syuio.kits.Assert;
import com.syuio.kits.ClassInfo;
import com.syuio.kits.CollectionConfigure;
import com.syuio.kits.Reflex;
import com.syuio.method.loader.MethodAnnotationLoader;
import com.syuio.method.loader.ProtocolMethodLoader;
import jdk.internal.org.objectweb.asm.ClassReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/2
 * Time: 23:53
 */
public final class CrAnnotationLoader implements CrLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(CrAnnotationLoader.class);
    private Syuio syuio = Syuio.syuio();
    private ProtocolMethodLoader protocolMethodLoader = new MethodAnnotationLoader();

    public CrAnnotationLoader() {
    }


    @Override
    public void load(Cr cr) {
        if (!syuio.isInit()) {
            Configuration configuration = syuio.getConfig();
            String protoclPackage = configuration.getProperties(Configuration.SyuioEnv.PROTOCOL_PACKAGE);
            String servicePackage = configuration.getProperties(Configuration.SyuioEnv.SERVICE_PACKAGE);
            Assert.notNull(servicePackage, "[init failed] - syuio.protocol.servicePackage must not be null");
            Assert.notNull(protoclPackage, "[init failed] - syuio.protocol.protocolPackage must not be null");
            //get Protocol Class of BasisProtol's Annotation
            Collection<ClassInfo> protocol = find(protoclPackage, configuration.isScanRecursively(), Protocol.class);
            Collection<ClassInfo> basisProtocol = find(protoclPackage, configuration.isScanRecursively(), BasisProtocol.class);
            Collection<ClassInfo> declaredProtocol = (Collection<ClassInfo>) Reflex.findInternalClassByAnno(basisProtocol, Protocol.class);
            if (CollectionConfigure.isNotEmpty(declaredProtocol))
                protocol.addAll(declaredProtocol);
            //ProtocolService load
            Collection<ClassInfo> service = find(servicePackage, configuration.isScanRecursively(), ProtocolService.class);

        }
    }


    private Collection<ClassInfo> find(String packageName, boolean recursive, Class<? extends Annotation>... annotation) {
        Collection<ClassInfo> classInfos = CollectionConfigure.newHashSet();
        ClassFinder classFinder = DynamicFinder.getClassFinder(packageName);
        Arrays.stream(annotation).forEach(anno -> {
            Collection<ClassInfo> classess = classFinder.getClass(packageName, anno, recursive);
            if (null != classess && 0 < classess.size())
                classInfos.addAll(classess);
        });
        return classInfos;
    }
}
