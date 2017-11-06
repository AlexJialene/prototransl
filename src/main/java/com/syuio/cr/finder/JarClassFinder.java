package com.syuio.cr.finder;

import com.syuio.kits.ClassInfo;
import com.syuio.kits.VolumeKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/4
 * Time: 19:53
 */
public class JarClassFinder implements ClassFinder {
    private static final Logger LOGGER = LoggerFactory.getLogger(JarClassFinder.class);

    @Override
    public Set<ClassInfo> getClass(String packageName, Class<? extends Annotation> anno, boolean recursive) {
        return this.getClass(packageName, null, anno, recursive);
    }

    @Override
    public Set<ClassInfo> getClass(String packageName, Class<?> clazz, Class<? extends Annotation> anno, boolean recursive) {
        Set<ClassInfo> classInfos = VolumeKit.newHashSet();
        String packageDirName = packageName.replace(".", "/");
        try {
            //Defines an enumerated collection and loops to process the URL in this directory
            Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                Set<ClassInfo> classess = this.getJarClasses(url, packageDirName, packageName, clazz, anno, recursive, VolumeKit.newHashSet());
                if (null != classess && 0 < classess.size())
                    classInfos.addAll(classess);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return classInfos;
    }

    private Set<ClassInfo> getJarClasses(URL url, String packageDirName, String packageName, Class<?> parent, Class<? extends Annotation> anno, boolean recursive, Set<ClassInfo> classess) throws IOException, ClassNotFoundException {
        JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
        //get an enumerated collection
        Enumeration<JarEntry> entries = jar.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            //Get an entity in the jar can be a directory and other documents such as META-INF and other documents
            String name = entry.getName();
            // if start with '/'
            if (name.charAt(0) == '/') {
                name = name.substring(1);
            }
            if (name.startsWith(packageDirName)) {
                int idx = name.lastIndexOf('/');
                if (-1 != idx)
                    packageName = name.substring(0, idx).replace('/', '.');
                if ((idx != -1) || recursive) {
                    if (name.endsWith(".class") && !entry.isDirectory()) {
                        //Remove the back of the ". Class" for the real class name
                        String className = name.substring(packageName.length() + 1, name.length() - 6);
                        Class<?> clazz = Class.forName(packageName + "." + className);
                        //Here can be extended
                        if (null != clazz && null != clazz.getAnnotation(anno)) {
                            classess.add(new ClassInfo(clazz));
                        }
                    }
                }
            }
        }
        return classess;
    }
}
