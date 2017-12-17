package com.prototransl;

import com.prototransl.config.Configuration;
import com.prototransl.cr.Cr;
import com.prototransl.cr.CrApplication;
import com.prototransl.cr.loader.CrAnnotationLoader;
import com.prototransl.proto.handle.ProtocolApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/2
 * Time: 18:05
 */
public class Transl {
    private static final Logger LOGGER = LoggerFactory.getLogger(Transl.class);
    private boolean isInit;
    private Cr ioc;
    private Configuration config;
    private ProtocolApp protocolApp;

    public Transl() {
        this.isInit = false;
        this.config = new Configuration();
        this.ioc = new CrApplication();
    }

    public static Transl transl() {
        return TranslHelper.s;
    }

    public boolean initialize() {
        this.getConfig().loadTranslConfig("classpath:prototransl.properties");
        if (!getConfig().isInit()) {
            this.getConfig().setEnvironment();
            this.getIoc().load(CrAnnotationLoader::new);
            this.init();
        }
        return true;
    }

    public Cr getIoc() {
        return ioc;
    }

    public Configuration getConfig() {
        return config;
    }

    protected void init() {
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

    private static final class TranslHelper {
        private static final Transl s = new Transl();
    }
}
