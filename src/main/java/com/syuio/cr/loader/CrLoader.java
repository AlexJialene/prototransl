package com.syuio.cr.loader;

import com.syuio.cr.CrApplication;

import java.util.function.Function;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/2
 * Time: 17:53
 */
@FunctionalInterface
public interface CrLoader {

    void load(CrApplication crApplication);

}
