package com.prototransl.cr.loader;

import com.prototransl.cr.Cr;
import com.prototransl.cr.CrApplication;

import java.util.function.Function;

/**
 * Created by IntelliJ IDEA.
 * User: Alex_
 * Date: 2017/11/2
 * Time: 17:53
 */
@FunctionalInterface
public interface CrLoader {

    void load(Cr ioc);

}
