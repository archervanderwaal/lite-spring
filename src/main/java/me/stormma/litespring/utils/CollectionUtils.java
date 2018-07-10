package me.stormma.litespring.utils;

import org.jetbrains.annotations.Contract;

import java.util.Collection;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class CollectionUtils {

    @Contract(value = "null -> true", pure = true)
    public static boolean isNullOrEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean isNotNullOrEmpty(Collection collection) {
        return !isNullOrEmpty(collection);
    }
}
