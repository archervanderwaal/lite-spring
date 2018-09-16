package com.archervanderwaal.litespring.utils;

import java.util.Objects;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class Assert {

    public static void assertNotNull(Object object, String message) {
        if (Objects.isNull(object)) {
            throw new IllegalArgumentException(message);
        }
    }
}
