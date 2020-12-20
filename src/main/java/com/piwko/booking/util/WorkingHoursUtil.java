package com.piwko.booking.util;

import com.piwko.booking.persistence.model.WorkingHours;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WorkingHoursUtil {

    private WorkingHoursUtil() {}

    public static Map<DayOfWeek, TimePair> initWorkingHoursByDayOfWeek(WorkingHours workingHours) {
        Map<DayOfWeek, TimePair> map = new EnumMap<>(DayOfWeek.class);
        if (workingHours.getMondayFrom() != null && workingHours.getMondayTo() != null) {
            map.put(DayOfWeek.MONDAY, new TimePair(workingHours.getMondayFrom(), workingHours.getMondayTo()));
        }
        if (workingHours.getTuesdayFrom() != null && workingHours.getTuesdayTo() != null) {
            map.put(DayOfWeek.TUESDAY, new TimePair(workingHours.getTuesdayFrom(), workingHours.getTuesdayTo()));
        }
        if (workingHours.getWednesdayFrom() != null && workingHours.getWednesdayTo() != null) {
            map.put(DayOfWeek.WEDNESDAY, new TimePair(workingHours.getWednesdayFrom(), workingHours.getWednesdayTo()));
        }
        if (workingHours.getThursdayFrom() != null && workingHours.getThursdayTo() != null) {
            map.put(DayOfWeek.THURSDAY, new TimePair(workingHours.getThursdayFrom(), workingHours.getThursdayTo()));
        }
        if (workingHours.getFridayFrom() != null && workingHours.getFridayTo() != null) {
            map.put(DayOfWeek.FRIDAY, new TimePair(workingHours.getFridayFrom(), workingHours.getFridayTo()));
        }
        if (workingHours.getSaturdayFrom() != null && workingHours.getSaturdayTo() != null) {
            map.put(DayOfWeek.SATURDAY, new TimePair(workingHours.getSaturdayFrom(), workingHours.getSaturdayTo()));
        }
        if (workingHours.getSundayFrom() != null && workingHours.getSundayTo() != null) {
            map.put(DayOfWeek.SUNDAY, new TimePair(workingHours.getSundayFrom(), workingHours.getSundayTo()));
        }
        return map;
    }

    public static void fillWorkingHours(Map<DayOfWeek, TimePair> map, WorkingHours workingHours) {
        map.forEach((k, v) -> {
            switch (k) {
                case MONDAY: {
                    workingHours.setMondayFrom(v.getFrom());
                    workingHours.setMondayTo(v.getTo());
                    break;
                }
                case TUESDAY: {
                    workingHours.setTuesdayFrom(v.getFrom());
                    workingHours.setTuesdayTo(v.getTo());
                    break;
                }
                case WEDNESDAY: {
                    workingHours.setWednesdayFrom(v.getFrom());
                    workingHours.setWednesdayTo(v.getTo());
                    break;
                }
                case THURSDAY: {
                    workingHours.setThursdayFrom(v.getFrom());
                    workingHours.setThursdayTo(v.getTo());
                    break;
                }
                case FRIDAY: {
                    workingHours.setFridayFrom(v.getFrom());
                    workingHours.setFridayTo(v.getTo());
                    break;
                }
                case SATURDAY: {
                    workingHours.setSaturdayFrom(v.getFrom());
                    workingHours.setSaturdayTo(v.getTo());
                    break;
                }
                case SUNDAY: {
                    workingHours.setSundayFrom(v.getFrom());
                    workingHours.setSundayTo(v.getTo());
                    break;
                }
            }
        });
        Set<DayOfWeek> daysToRemove = new HashSet<>();
        map.forEach((k, v) -> {
            if (v.getFrom() == null && v.getTo() == null) {
                daysToRemove.add(k);
            }
        });
        daysToRemove.forEach(map::remove);
    }


    public static void updateWorkingHours(WorkingHours existingWorkingHours, WorkingHours workingHours) {
        Map<DayOfWeek, TimePair> existingMap = existingWorkingHours.getWorkingHoursByDayOfWeek();
        for (Map.Entry<DayOfWeek, TimePair> entry : workingHours.getWorkingHoursByDayOfWeek().entrySet()) {
            if (existingMap.get(entry.getKey()) != null) {
                existingMap.replace(entry.getKey(), entry.getValue());
            } else {
                existingMap.put(entry.getKey(), entry.getValue());
            }
        }
        for (Map.Entry<DayOfWeek, TimePair> entry : existingMap.entrySet()) {
            if (!workingHours.getWorkingHoursByDayOfWeek().containsKey(entry.getKey())) {
                existingMap.get(entry.getKey()).setFrom(null);
                existingMap.get(entry.getKey()).setTo(null);
            }
        }
        existingWorkingHours.setWorkingHoursByDayOfWeek(existingMap);
    }

    public static boolean isWithinWorkingHours(LocalDateTime date, WorkingHours workingHours) {
        TimePair pairByDayOfWeek = workingHours.getWorkingHoursByDayOfWeek().get(date.getDayOfWeek());
        if (pairByDayOfWeek == null) {
            return false;
        }
        LocalTime localTime = LocalTime.of(date.getHour(), date.getMinute());
        return (localTime.isAfter(pairByDayOfWeek.getFrom()) && localTime.isBefore(pairByDayOfWeek.getTo()));
    }

    public static boolean isWithinWorkingHours(TimePair empTimePair, TimePair locTimePair) {
        if (locTimePair == null) {
            return false;
        }
        return ((empTimePair.getFrom().isAfter(locTimePair.getFrom()) || empTimePair.getFrom().equals(locTimePair.getFrom())) &&
                (empTimePair.getTo().isBefore(locTimePair.getTo()) || empTimePair.getTo().equals(locTimePair.getTo())));
    }

}
