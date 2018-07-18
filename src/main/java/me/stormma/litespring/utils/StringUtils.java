package me.stormma.litespring.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;
import java.util.List;

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

    public static String[] tokenizeToStringArray(String str, String delimiters) {
        return tokenizeToStringArray(str, delimiters, true, true);
    }

    @Nullable
    public static String[] tokenizeToStringArray(
            String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {
        if (isNull(str)) {
            return null;
        }

        StringTokenizer st = new StringTokenizer(str, delimiters);
        List<String> tokens = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (trimTokens) {
                token = token.trim();
            }
            if (!ignoreEmptyTokens || token.length() > 0) {
                tokens.add(token);
            }
        }
        return toStringArray(tokens);
    }

    @Contract(value = "null -> true; !null -> false", pure = true)
    private static boolean isNull(String str) {
        return str == null;
    }

    @Contract("null -> null; !null -> !null")
    public static String[] toStringArray(Collection<String> collection) {
        if (collection == null) {
            return null;
        }
        return collection.toArray(new String[collection.size()]);
    }
}
