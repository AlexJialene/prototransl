package com.syuio.config;

import com.syuio.kits.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/2
 * Time: 18:07
 * system configuration
 */
public class Configuration {
    private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);
    private String encoding = "UTF-8";
    private String basePackage;
    private boolean isInit = false;
    private boolean isScanRecursively = false;
    private Integer port;
    private Config config = new Config();

    public Configuration() {
    }

    public void loadSyuioConfig(String location) {
        Assert.notBlank(location);
        this.config.add(location);
    }

    public void loadSystemConfig() {
        this.config.loadSystemProperties();
    }

    protected Config getConfig() {
        return config;
    }

    public void setEnvironment() {
        if (null != config && !this.isInit) {
            this.basePackage = this.getConfig().get(SyuioEnv.BASE_PACKAGE.key);
            this.isScanRecursively = this.getConfig().getBoolean(SyuioEnv.SCAN_RECURSIVELY.key, true);
            this.port = this.getConfig().getInteger(SyuioEnv.PORT.key, 9876);

            LOGGER.info("The built-in port is {}", getPort());

            this.isInit = true;
        }

    }

    public String getBasePackage() {
        return basePackage;
    }

    public boolean isScanRecursively() {
        return isScanRecursively;
    }

    public Integer getPort() {
        return port;
    }

    public boolean isInit() {
        return isInit;
    }

    public String getProperties(SyuioEnv env) {
        return getConfig().get(env.key);
    }

    public enum SyuioEnv {
        BASE_PACKAGE("syuio.basePackage"),
        PROTOCOL_PACKAGE("syuio.protocol.protocolPackage"),
        SERVICE_PACKAGE("syuio.protocol.servicePackage"),
        SCAN_RECURSIVELY("syuio.scan.recursively"),
        PORT("syuio.port");

        String key;

        SyuioEnv(String key) {
            this.key = key;
        }
    }
}
