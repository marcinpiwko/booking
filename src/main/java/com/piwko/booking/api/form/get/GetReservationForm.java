package com.piwko.booking.api.form.get;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GetReservationForm {

    private String username;

    private boolean virtual;

    private String locationCode;

    private String employee;

    private String serviceCode;

    private LocalDateTime date;

    private String status;

    private LocalDateTime creationDate;

    private LocalDateTime cancellationDate;

    private String cancellationReason;
}
