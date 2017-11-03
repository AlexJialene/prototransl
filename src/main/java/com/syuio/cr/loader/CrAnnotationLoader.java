package com.syuio.cr.loader;

import com.syuio.Syuio;
import com.syuio.cr.Cr;
import com.syuio.cr.CrApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/2
 * Time: 23:53
 */
public final class CrAnnotationLoader implements CrLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(CrAnnotationLoader.class);
    private Syuio syuio = Syuio.syuio();
    public CrAnnotationLoader() {
    }

    @Override
    public void load(CrApplication crApplication) {
        return;
    }
}
