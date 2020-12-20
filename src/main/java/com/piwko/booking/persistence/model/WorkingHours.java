package com.piwko.booking.persistence.model;

import com.piwko.booking.util.TimePair;
import com.piwko.booking.util.WorkingHoursUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Map;

@Entity(name = "WORKING_HOURS")
@Table(name = "WORKING_HOURS")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class WorkingHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "WORKING_HOURS_ID_GENERATOR")
    @SequenceGenerator(name = "WORKING_HOURS_ID_GENERATOR", sequenceName = "WRH_SEQ")
    @Column(name = "WRH_ID", nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "WRH_MONDAY_F")
    private LocalTime mondayFrom;

    @Column(name = "WRH_MONDAY_T")
    private LocalTime mondayTo;

    @Column(name = "WRH_TUESDAY_F")
    private LocalTime tuesdayFrom;

    @Column(name = "WRH_TUESDAY_T")
    private LocalTime tuesdayTo;

    @Column(name = "WRH_WEDNESDAY_F")
    private LocalTime wednesdayFrom;

    @Column(name = "WRH_WEDNESDAY_T")
    private LocalTime wednesdayTo;

    @Column(name = "WRH_THURSDAY_F")
    private LocalTime thursdayFrom;

    @Column(name = "WRH_THURSDAY_T")
    private LocalTime thursdayTo;

    @Column(name = "WRH_FRIDAY_F")
    private LocalTime fridayFrom;

    @Column(name = "WRH_FRIDAY_T")
    private LocalTime fridayTo;

    @Column(name = "WRH_SATURDAY_F")
    private LocalTime saturdayFrom;

    @Column(name = "WRH_SATURDAY_T")
    private LocalTime saturdayTo;

    @Column(name = "WRH_SUNDAY_F")
    private LocalTime sundayFrom;

    @Column(name = "WRH_SUNDAY_T")
    private LocalTime sundayTo;

    @Transient
    private Map<DayOfWeek, TimePair> workingHoursByDayOfWeek;

    public Map<DayOfWeek, TimePair> getWorkingHoursByDayOfWeek() {
        if (this.workingHoursByDayOfWeek == null || this.workingHoursByDayOfWeek.isEmpty()) {
            this.workingHoursByDayOfWeek = WorkingHoursUtil.initWorkingHoursByDayOfWeek(this);
        }
        return workingHoursByDayOfWeek;
    }

    public void setWorkingHoursByDayOfWeek(Map<DayOfWeek, TimePair> map) {
        WorkingHoursUtil.fillWorkingHours(map, this);
        this.workingHoursByDayOfWeek = map;
    }
}
