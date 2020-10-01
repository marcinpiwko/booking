package com.piwko.booking.util;

import java.util.UUID;
import java.util.regex.Matcher;

public class StringUtil {

    private StringUtil() {

    }

    public static String replace(String pMessage, Object[] pParams) {
        if (pParams != null && pMessage != null) {
            for (int i = 0; i < pParams.length; i++) {
                pMessage = pMessage.replaceAll("\\{" + i + "}", (pParams[i] != null ? Matcher.quoteReplacement(pParams[i].toString()) : "null"));
            }
        }
        return pMessage;
    }

    public static String getRandomString() {
        return UUID.randomUUID().toString();
    }

    public static boolean isEmpty(String string) {
        return (string == null || string.isEmpty());
    }
}
