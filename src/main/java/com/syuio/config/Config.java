package com.syuio.config;

import com.syuio.kits.Assert;
import com.syuio.kits.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/3
 * Time: 10:26
 */
public class Config {
    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);
    private Map<String, String> config = Collections.synchronizedMap(new HashMap<>(24));

    public Config() {
    }

    public Config(Map<String, String> config) {
        this.config = config;
    }

    public Config(Properties props) {
        Iterator var2 = props.stringPropertyNames().iterator();

        while (var2.hasNext()) {
            String key = (String) var2.next();
            String value = props.getProperty(key);
            this.config.put(key, value);
        }

    }

    public static Config load(String location) {
        return (new Config()).loadLoaction(location);
    }

    public static ClassLoader getDefault() {
        ClassLoader loader = null;

        try {
            loader = Thread.currentThread().getContextClassLoader();
        } catch (Exception var3) {
            ;
        }

        if (loader == null) {
            loader = Config.class.getClassLoader();
            if (loader == null) {
                try {
                    loader = ClassLoader.getSystemClassLoader();
                } catch (Exception var2) {
                    ;
                }
            }
        }

        return loader;
    }

    public Config load(Properties props) {
        Assert.notNull(props, "properties not null");
        Iterator it = props.keySet().iterator();

        while (it.hasNext()) {
            String key = it.next().toString();
            String value = props.getProperty(key);
            this.config.put(key, value);
        }

        return this;
    }

    private Config loadLoaction(String location) {
        if (location.startsWith("classpath:")) {
            location = location.substring("classpath:".length());
            return this.loadClasspath(location);
        } else if (location.startsWith("file:")) {
            location = location.substring("file:".length());
            return this.load(new File(location));
        } else {
            return this.loadClasspath(location);
        }
    }

    public void add(String location) {
        Config config = this.loadLoaction(location);
        if (null != config) {
            this.config.putAll(config.asMap());
        }

    }

    public void add(Config config) {
        if (null != config) {
            this.config.putAll(config.asMap());
        }

    }

    public void addAll(Map<String, String> configMap) {
        if (null != configMap) {
            this.config.putAll(configMap);
        }

    }

    private Config loadClasspath(String classpath) {
        if (classpath.startsWith("/")) {
            classpath = classpath.substring(1);
        }

        InputStream is = getDefault().getResourceAsStream(classpath);
        LOGGER.debug("Load config [classpath:" + classpath + "]");
        return this.loadInputStream(is, classpath);
    }

    public Config load(File file) {
        try {
            LOGGER.debug("Load config [file:" + file.getPath() + "]");
            return this.loadInputStream(new FileInputStream(file), file.getName());
        } catch (IOException var3) {
            throw new IllegalStateException(var3);
        }
    }

    private Config loadInputStream(InputStream is, String location) {
        if (is == null) {
            LOGGER.warn("InputStream not found: " + location);
            return new Config();
        } else {
            Config var4;
            try {
                Properties config = new Properties();
                config.load(is);
                this.load(config);
                var4 = this;
            } catch (IOException var8) {
                throw new IllegalStateException(var8);
            } finally {
                if (null != is) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return var4;
        }
    }

    public Config loadSystemProperties() {
        return this.load(System.getProperties());
    }

    public Map<String, String> asMap() {
        return this.config;
    }

    public String get(String key) {
        String prop = this.config.get(key);
        if (null == prop)
            LOGGER.warn("[Warn] - cannot load properties value :{}={}", key, this.config.get(key));
        return prop;
    }

    public String get(String key, String defaultValue) {
        return null != this.config.get(key) ? this.config.get(key) : defaultValue;
    }

    public Config put(String key, Object value) {
        this.config.put(key, value.toString());
        return this;
    }

    public Config set(String key, Object value) {
        return this.put(key, value);
    }

    public Boolean getBoolean(String key, boolean defaultValue) {
        return Boolean.valueOf(null != this.getBoolean(key) ? this.getBoolean(key).booleanValue() : defaultValue);
    }

    public Boolean getBoolean(String key) {
        String value = this.get(key);
        return StringUtils.isNotBlank(value) ? Boolean.valueOf(value) : null;
    }

    public Integer getInteger(String key, Integer defaultValue) {
        return null == this.getInteger(key) ? defaultValue : this.getInteger(key);
    }

    public Integer getInteger(String key) {
        String value = this.get(key);
        return StringUtils.isNotBlank(value) ? Integer.valueOf(key) : null;
    }
}