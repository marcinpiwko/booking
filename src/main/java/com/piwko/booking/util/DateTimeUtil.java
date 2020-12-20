package com.piwko.booking.util;

import com.piwko.booking.util.exception.ApplicationException;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeUtil {

    private DateTimeUtil() {}

    public static final String HOUR_PATTERN = "^([0-1][0-9]|[2][0-3]):([0-5][0-9])$";

    public static final String HOUR_RANGE_PATTERN = "^([0-1][0-9]|[2][0-3]):([0-5][0-9])-([0-1][0-9]|[2][0-3]):([0-5][0-9])$";

    public static LocalDateTime getCurrentDate() {
        return LocalDateTime.now();
    }

    public static LocalTime getLocalTime(String time) {
        if (!time.matches(HOUR_PATTERN)) {
            throw new ApplicationException("Time " + time + " does not match the regex: " + HOUR_PATTERN);
        }
        return LocalTime.of(Integer.parseInt(time.split(":")[0]), Integer.parseInt(time.split(":")[1]));
    }
}