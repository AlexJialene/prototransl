package com.prototransl.cr.loader;

import com.prototransl.Transl;
import com.prototransl.config.Configuration;
import com.prototransl.cr.Cr;
import com.prototransl.cr.DynamicFinder;
import com.prototransl.cr.finder.ClassFinder;
import com.prototransl.itinerary.ItineraryBuilder;
import com.prototransl.kits.*;
import com.prototransl.proto.ProtoApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/2
 * Time: 23:53
 */
public final class CrAnnotationLoader implements CrLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(CrAnnotationLoader.class);
    private Transl transl = Transl.transl();

    public CrAnnotationLoader() {
    }


    @Override
    public void load(Cr cr) {
        if (!transl.isInit()) {
            Configuration configuration = transl.getConfig();
            String protoclPackage = configuration.getProperties(Configuration.TranslEnv.PROTOCOL_PACKAGE);
            String servicePackage = configuration.getProperties(Configuration.TranslEnv.SERVICE_PACKAGE);
            Assert.notNull(servicePackage, "[init failed] - prototransl.protocol.servicePackage must not be null");
            Assert.notNull(protoclPackage, "[init failed] - prototransl.protocol.protocolPackage must not be null");

            //get Protocol Class of BasisProtol's Annotation
            Collection<ClassInfo> protocol = find(protoclPackage, configuration.isScanRecursively(), SystemKits.PROTOCOL_CLASS);
            //Collection<ClassInfo> basisProtocol = find(protoclPackage, configuration.isScanRecursively(), SystemKits.BASIS_PROTOCOL_CLASS);
            //Collection<ClassInfo> declaredProtocol = Reflex.findInternalClassByAnno(basisProtocol, SystemKits.PROTOCOL_CLASS);
            Collection<ClassInfo> service = find(servicePackage, configuration.isScanRecursively(), SystemKits.PROTOCOL_SERVICE_CLASS);
            /*if (VolumeKit.isNotEmpty(declaredProtocol)){
                protocol.addAll(declaredProtocol);
            }*/
            ProtoApplication protoApplication = new ProtoApplication(cr, configuration.getPackCapacity(), configuration.getByteOrder());
            protoApplication.initProtocol(protocol, new ItineraryBuilder(service).build());
        }
    }


    private Collection<ClassInfo> find(String packageName, boolean recursive, Class<? extends Annotation>... annotation) {
        Collection<ClassInfo> classInfos = VolumeKit.newHashSet();
        ClassFinder classFinder = DynamicFinder.getClassFinder(packageName);
        Arrays.stream(annotation).forEach(anno -> {
            Collection<ClassInfo> classess = classFinder.getClass(packageName, anno, recursive);
            if (null != classess && 0 < classess.size())
                classInfos.addAll(classess);
        });
        return classInfos;
    }
}
