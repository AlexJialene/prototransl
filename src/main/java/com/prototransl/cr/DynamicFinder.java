package com.prototransl.cr;

import com.prototransl.cr.finder.ClassFinder;
import com.prototransl.cr.finder.ClassPathFinder;
import com.prototransl.cr.finder.JarClassFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/4
 * Time: 20:30
 */
public class DynamicFinder {
    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicFinder.class);
    private static final ClassFinder CLASS_FINDER = new ClassPathFinder();
    private static final ClassFinder JAR_CLASS_FINDER = new JarClassFinder();

    public static ClassFinder getClassFinder(String packName) {
        return judgeClassPath(packName) ? CLASS_FINDER : JAR_CLASS_FINDER;
    }

    private static boolean judgeClassPath(String packName) {
        String packageDirName = packName.replace(".", "/");
        try {
            Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            if (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                return "jar".equals(protocol) ? false : true;
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }
}
