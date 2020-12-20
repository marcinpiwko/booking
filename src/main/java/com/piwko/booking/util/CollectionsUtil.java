package com.piwko.booking.util;

import java.util.List;
import java.util.Set;

public class CollectionsUtil {

    private CollectionsUtil() {}

    public static boolean isEmpty(List<?> list) {
        return (list == null || list.isEmpty());
    }

    public static boolean isEmpty(Set<?> set) {
        return (set == null || set.isEmpty());
    }
}
