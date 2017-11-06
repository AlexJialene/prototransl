package com.syuio.cr.finder;

import com.syuio.annotation.Protocol;
import com.syuio.kits.Assert;
import com.syuio.kits.ClassInfo;
import com.syuio.kits.VolumeKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/4
 * Time: 18:01
 */
public class ClassPathFinder implements ClassFinder {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassPathFinder.class);

    @Override
    public Set<ClassInfo> getClass(String packageName, Class<? extends Annotation> anno, boolean recursive) {
        return getClass(packageName, null, anno, recursive);
    }

    @Override
    public Set<ClassInfo> getClass(String packageName, Class<?> clazz, Class<? extends Annotation> anno, boolean recursive) {
        Assert.notBlank(packageName);
        Set<ClassInfo> set = VolumeKit.newHashSet();
        packageName = packageName.replace(".", "/");
        try {
            Enumeration dirs = Thread.currentThread().getContextClassLoader().getResources(packageName);
            //Enumeration dirs = this.getClass().getClassLoader().getResources(packageName);
            while (dirs.hasMoreElements()) {
                URL url = (URL) dirs.nextElement();
                String filePath = new URI(url.getFile()).getPath();
                //String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                //LOGGER.info("filepath = {}" ,filePath);
                Set<ClassInfo> classInfos = getClassByPackagePath(packageName, filePath, clazz, anno, recursive, VolumeKit.newHashSet());
                if (null != classInfos && 0 < classInfos.size())
                    set.addAll(classInfos);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage() , e);
        } catch (URISyntaxException e) {
            LOGGER.error(e.getMessage() , e);
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage() , e);
        }
        return set;
    }

    private Set<ClassInfo> getClassByPackagePath(String packageName, String filePath, Class<?> parent, Class<? extends Annotation> anno, boolean recursive, Set<ClassInfo> ts) throws ClassNotFoundException {
        File dir = new File(filePath);
        if (!dir.exists() || !dir.isDirectory())
            LOGGER.warn("the package [{}] not found ", filePath);
        File[] files = dir.listFiles(file -> recursive && file.isDirectory() || file.getName().endsWith(".class"));
        if (null != files && 0 < files.length) {
            for (File file : files) {
                if (file.isDirectory())
                    getClassByPackagePath(packageName + "." + file.getName(), file.getAbsolutePath(), parent, anno, recursive, ts);
                else {
                    String className = file.getName().substring(0, file.getName().length() - 6);
                    Class clazz = Class.forName(packageName + "." + className);
                    //Here can be extended
                    if (null != clazz && null != clazz.getAnnotation(anno)) {
                        ts.add(new ClassInfo(clazz));
                    }
                }
            }
        }
        return ts;
    }

    public static void main(String[] args) {
        ClassPathFinder classPathFinder = new ClassPathFinder();
        Set<ClassInfo> classInfos = classPathFinder.getClass("model", Protocol.class, true);
        classInfos.forEach((classInfo -> {
            System.out.println(classInfo.getClassName());
        }));
    }
}
