package com.piwko.booking.service.imports.translator;

import com.piwko.booking.persistence.EntityFactory;
import com.piwko.booking.persistence.model.WorkingHours;
import com.piwko.booking.util.DateTimeUtil;
import com.piwko.booking.util.TimePair;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class WorkingHoursCsvTranslator {

    public WorkingHours translate(List<String> workingHoursCsv) {
        WorkingHours workingHours = EntityFactory.newEntityInstance(WorkingHours.class);
        Map<DayOfWeek, TimePair> map = new EnumMap<>(DayOfWeek.class);
        for (String workingHoursDay : workingHoursCsv) {
            String[] workingDaySplit = workingHoursDay.split("=");
            DayOfWeek dayOfWeek = DayOfWeek.valueOf(workingDaySplit[0].toUpperCase());
            String from = workingDaySplit[1].split("-")[0];
            String to = workingDaySplit[1].split("-")[1];
            map.put(dayOfWeek, new TimePair(DateTimeUtil.getLocalTime(from), DateTimeUtil.getLocalTime(to)));
        }
        workingHours.setWorkingHoursByDayOfWeek(map);
        return workingHours;
    }
}
