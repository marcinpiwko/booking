package com.piwko.booking.util;

import java.util.List;

public class ArraysUtil {

    private ArraysUtil() {}

    public static boolean isEmpty(List<?> list) {
        return (list == null || list.isEmpty());
    }
}
