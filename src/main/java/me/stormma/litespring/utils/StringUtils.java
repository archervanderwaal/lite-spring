package me.stormma.litespring.utils;

import org.jetbrains.annotations.Contract;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class StringUtils {

    @Contract("null -> true")
    public static boolean isNullOrEmpty(String val) {
        return val == null || val.equals("");
    }

    @Contract("null -> false")
    public static boolean isNotNullOrEmpty(String val) {
        return !isNullOrEmpty(val);
    }

    public static boolean hasLength(String val) {
        return hasLength((CharSequence) val);
    }

    @Contract("null -> false")
    public static boolean hasLength(CharSequence sequence) {
        return sequence != null && sequence.length() > 0;
    }

    public static boolean hasText(String val) {
        return hasText((CharSequence) val);
    }

    public static boolean hasText(CharSequence sequence) {
        if (!hasLength(sequence)) {
            return false;
        }
        for (int i = 0; i < sequence.length(); i++) {
            if (!Character.isWhitespace(sequence.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static String trimAllWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        int index = 0;
        while (sb.length() > index) {
            if (Character.isWhitespace(sb.charAt(index))) {
                sb.deleteCharAt(index);
            } else {
                index++;
            }
        }
        return sb.toString();
    }
}
