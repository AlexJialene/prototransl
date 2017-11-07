package com.syuio;

import com.syuio.config.Configuration;
import com.syuio.cr.Cr;
import com.syuio.cr.CrApplication;
import com.syuio.cr.loader.CrAnnotationLoader;
import com.syuio.proto.handle.ProtocolApp;
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
    private Cr ioc;
    private Configuration config;
    private ProtocolApp protocolApp;

    public Syuio() {
        this.isInit = false;
        this.config = new Configuration();
        this.ioc = new CrApplication();
    }

    public static Syuio syuio() {
        return SyuioHelper.s;
    }

    public boolean start() {
        this.getConfig().loadSyuioConfig("classpath:syuio.properties");
        if (!getConfig().isInit()) {
            this.getConfig().setEnvironment();
            this.getIoc().load(CrAnnotationLoader::new);
            //this.init();
        }
        return true;
    }

    public Cr getIoc() {
        return ioc;
    }

    public Configuration getConfig() {
        return config;
    }

    public void init() {
        if (!this.isInit)
            this.isInit = true;
    }

    public boolean isInit() {
        return isInit;
    }

    public ProtocolApp createProtocolApp() {
        return this.protocolApp;
    }

    public void setProtocolApp(ProtocolApp protocolApp) {
        this.protocolApp = protocolApp;
    }

    private static final class SyuioHelper {
        private static final Syuio s = new Syuio();
    }
}
