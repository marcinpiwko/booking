package com.piwko.booking.api.form.patch;

import com.piwko.booking.api.form.interfaces.PostPatchWorkingHours;
import com.piwko.booking.util.DateTimeUtil;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class PatchWorkingHoursForm implements PostPatchWorkingHours {

    @Pattern(regexp = DateTimeUtil.HOUR_RANGE_PATTERN)
    private String monday;

    @Pattern(regexp = DateTimeUtil.HOUR_RANGE_PATTERN)
    private String tuesday;

    @Pattern(regexp = DateTimeUtil.HOUR_RANGE_PATTERN)
    private String wednesday;

    @Pattern(regexp = DateTimeUtil.HOUR_RANGE_PATTERN)
    private String thursday;

    @Pattern(regexp = DateTimeUtil.HOUR_RANGE_PATTERN)
    private String friday;

    @Pattern(regexp = DateTimeUtil.HOUR_RANGE_PATTERN)
    private String saturday;

    @Pattern(regexp = DateTimeUtil.HOUR_RANGE_PATTERN)
    private String sunday;
}
