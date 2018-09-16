package com.archervanderwaal.litespring.test.v5.util;

import com.archervanderwaal.litespring.utils.CollectionUtils;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class MessageTracker {

    private static List<String> MESSAGES = new ArrayList<>();

    public static void addMessage(String message) {
        MESSAGES.add(message);
    }

    public static void clearMessages() {
        if (CollectionUtils.isNotNullOrEmpty(MESSAGES)) {
            MESSAGES.clear();
        }
    }

    @Contract(pure = true)
    public static List<String> getMessages() {
        return MESSAGES;
    }
}
