package com.prototransl.config;

import com.prototransl.kits.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteOrder;

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
    private boolean isScanRecursively;
    private Integer port;
    private Integer packCapacity;
    private ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
    private Config config = new Config();

    public Configuration() {
    }

    public void loadTranslConfig(String location) {
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
            this.basePackage = this.getConfig().get(TranslEnv.BASE_PACKAGE.key);
            this.isScanRecursively = this.getConfig().getBoolean(TranslEnv.SCAN_RECURSIVELY.key, true);
            this.port = this.getConfig().getInteger(TranslEnv.PORT.key, 9876);
            this.packCapacity = this.getConfig().getInteger(TranslEnv.PACK_CAPACITY.key, 512);
            if (this.getConfig().getBoolean(TranslEnv.BUFFER_ORDER.key , false)) {
                this.byteOrder = ByteOrder.LITTLE_ENDIAN;
            }
            this.isInit = true;
        }

    }

    public String getBasePackage() {
        return basePackage;
    }

    public boolean isScanRecursively() {
        return isScanRecursively;
    }

    public Integer getPackCapacity() {
        return packCapacity;
    }

    public ByteOrder getByteOrder() {
        return byteOrder;
    }

    public Integer getPort() {
        return port;
    }

    public boolean isInit() {
        return isInit;
    }

    public String getProperties(TranslEnv env) {
        return getConfig().get(env.key);
    }

    public enum TranslEnv {
        BASE_PACKAGE("prototransl.basePackage"),
        PROTOCOL_PACKAGE("prototransl.protocol.protocolPackage"),
        SERVICE_PACKAGE("prototransl.protocol.servicePackage"),
        SCAN_RECURSIVELY("prototransl.scan.recursively"),
        PACK_CAPACITY("prototransl.pack.capacity"),
        BUFFER_ORDER("prototransl.buffer.littleEndian"),
        PORT("prototransl.port");

        String key;

        TranslEnv(String key) {
            this.key = key;
        }
    }
}
