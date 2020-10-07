package com.piwko.booking.api.form.get;

import lombok.Data;

@Data
public class GetLocationForm {

    private Long id;

    private String code;

    private String name;

    private GetAddressForm address;
}

