package com.piwko.booking.api.translator;

import com.piwko.booking.api.form.get.GetWorkingHoursForm;
import com.piwko.booking.api.form.interfaces.PostPatchWorkingHours;
import com.piwko.booking.persistence.EntityFactory;
import com.piwko.booking.persistence.model.WorkingHours;
import com.piwko.booking.util.DateTimeUtil;
import com.piwko.booking.util.StringUtil;
import com.piwko.booking.util.TimePair;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.EnumMap;
import java.util.Map;

@Component
public class WorkingHoursTranslator {

    public GetWorkingHoursForm translate(WorkingHours workingHours) {
        Map<DayOfWeek, TimePair> map = workingHours.getWorkingHoursByDayOfWeek();
        GetWorkingHoursForm workingHoursForm = new GetWorkingHoursForm();
        if (map.containsKey(DayOfWeek.MONDAY)) {
            TimePair pair = map.get(DayOfWeek.MONDAY);
            workingHoursForm.setMonday(pair.getFrom() + "-" + pair.getTo());
        }
        if (map.containsKey(DayOfWeek.TUESDAY)) {
            TimePair pair = map.get(DayOfWeek.TUESDAY);
            workingHoursForm.setTuesday(pair.getFrom() + "-" + pair.getTo());
        }
        if (map.containsKey(DayOfWeek.WEDNESDAY)) {
            TimePair pair = map.get(DayOfWeek.WEDNESDAY);
            workingHoursForm.setWednesday(pair.getFrom() + "-" + pair.getTo());
        }
        if (map.containsKey(DayOfWeek.THURSDAY)) {
            TimePair pair = map.get(DayOfWeek.THURSDAY);
            workingHoursForm.setThursday(pair.getFrom() + "-" + pair.getTo());
        }
        if (map.containsKey(DayOfWeek.FRIDAY)) {
            TimePair pair = map.get(DayOfWeek.FRIDAY);
            workingHoursForm.setFriday(pair.getFrom() + "-" + pair.getTo());
        }
        if (map.containsKey(DayOfWeek.SATURDAY)) {
            TimePair pair = map.get(DayOfWeek.SATURDAY);
            workingHoursForm.setSaturday(pair.getFrom() + "-" + pair.getTo());
        }
        if (map.containsKey(DayOfWeek.SUNDAY)) {
            TimePair pair = map.get(DayOfWeek.SUNDAY);
            workingHoursForm.setSunday(pair.getFrom() + "-" + pair.getTo());
        }
        return workingHoursForm;
    }

    public WorkingHours translate(PostPatchWorkingHours workingHoursForm) {
        WorkingHours workingHours = EntityFactory.newEntityInstance(WorkingHours.class);
        Map<DayOfWeek, TimePair> map = new EnumMap<>(DayOfWeek.class);
        if (!StringUtil.isEmpty(workingHoursForm.getMonday())) {
            String[] split = workingHoursForm.getMonday().split("-");
            map.put(DayOfWeek.MONDAY, new TimePair(DateTimeUtil.getLocalTime(split[0]), DateTimeUtil.getLocalTime(split[1])));
        }
        if (!StringUtil.isEmpty(workingHoursForm.getTuesday())) {
            String[] split = workingHoursForm.getTuesday().split("-");
            map.put(DayOfWeek.TUESDAY, new TimePair(DateTimeUtil.getLocalTime(split[0]), DateTimeUtil.getLocalTime(split[1])));
        }
        if (!StringUtil.isEmpty(workingHoursForm.getWednesday())) {
            String[] split = workingHoursForm.getWednesday().split("-");
            map.put(DayOfWeek.WEDNESDAY, new TimePair(DateTimeUtil.getLocalTime(split[0]), DateTimeUtil.getLocalTime(split[1])));
        }
        if (!StringUtil.isEmpty(workingHoursForm.getThursday())) {
            String[] split = workingHoursForm.getThursday().split("-");
            map.put(DayOfWeek.THURSDAY, new TimePair(DateTimeUtil.getLocalTime(split[0]), DateTimeUtil.getLocalTime(split[1])));
        }
        if (!StringUtil.isEmpty(workingHoursForm.getFriday())) {
            String[] split = workingHoursForm.getFriday().split("-");
            map.put(DayOfWeek.FRIDAY, new TimePair(DateTimeUtil.getLocalTime(split[0]), DateTimeUtil.getLocalTime(split[1])));
        }
        if (!StringUtil.isEmpty(workingHoursForm.getSaturday())) {
            String[] split = workingHoursForm.getSaturday().split("-");
            map.put(DayOfWeek.SATURDAY, new TimePair(DateTimeUtil.getLocalTime(split[0]), DateTimeUtil.getLocalTime(split[1])));
        }
        if (!StringUtil.isEmpty(workingHoursForm.getSunday())) {
            String[] split = workingHoursForm.getSunday().split("-");
            map.put(DayOfWeek.SUNDAY, new TimePair(DateTimeUtil.getLocalTime(split[0]), DateTimeUtil.getLocalTime(split[1])));
        }
        workingHours.setWorkingHoursByDayOfWeek(map);
        return workingHours;
    }
}
