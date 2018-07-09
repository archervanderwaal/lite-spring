package me.stormma.litespring.utils;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class StringUtils {

    public static boolean isNullOrEmpty(String val) {
        return val == null || val.equals("");
    }

    public static boolean isNotNullOrEmpty(String val) {
        return !isNullOrEmpty(val);
    }
}
