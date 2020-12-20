package com.piwko.booking.api.form.get;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetLocationForm {

    private Long id;

    private String code;

    private String name;

    private String companyCode;

    private GetAddressForm address;

    private GetWorkingHoursForm workingHours;
}

