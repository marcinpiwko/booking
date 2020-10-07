package com.piwko.booking.api.form.get;

import lombok.Data;

@Data
public class GetAddressForm {

    private Long id;

    private String street;

    private String city;

    private String country;

    private String postalCode;

    private String phoneNumber;

    private String email;
}

