package com.archervanderwaal.litespring.aop;

import net.sf.cglib.core.DefaultNamingPolicy;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class LiteSpringNamingPolicy extends DefaultNamingPolicy {

    public static final LiteSpringNamingPolicy INSTANCE = new LiteSpringNamingPolicy();

    public LiteSpringNamingPolicy() {
    }

    protected String getTag() {
        return "ByLiteSpringSpringCGLIB";
    }
}
