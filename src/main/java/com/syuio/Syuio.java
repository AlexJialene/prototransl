package com.syuio;

import com.syuio.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/2
 * Time: 18:05
 */
public class Syuio {
    private static final Logger LOGGER = LoggerFactory.getLogger(Syuio.class);
    private boolean isInit;
    private boolean enableServer;
    private Configuration config;
    public Syuio() {
        this.isInit = false;
        this.enableServer = false;
        this.config = new Configuration();
    }

    public static Syuio syuio() {
        return SyuioHelper.s;
    }

    public boolean start(Class<?> applicationClass) {
        //TODO 初始化所有注解配置好容器
        config.setApplicationClass(applicationClass);

        return true;
    }

    private static final class SyuioHelper {
        private static final Syuio s = new Syuio();
    }

}
