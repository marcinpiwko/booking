package com.piwko.booking.util;

import com.piwko.booking.util.exception.ApplicationException;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Getter
@Setter
public class TimePair {

    public TimePair(@NotNull LocalTime from, @NotNull LocalTime to) {
        if (!from.equals(LocalTime.of(0, 0)) && !to.equals(LocalTime.of(0, 0)) && from.isAfter(to)) {
            throw new ApplicationException("Can not create TimePair - date from " + from + " is after date to " + to);
        }
        if (from.equals(to)) {
            throw new ApplicationException("Can not create TimePair - date from is equals to date to");
        }
        this.from = from;
        this.to = to;
    }

    private LocalTime from;

    private LocalTime to;

}
