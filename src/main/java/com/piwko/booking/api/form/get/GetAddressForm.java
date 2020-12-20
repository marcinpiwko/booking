package com.piwko.booking.api.form.get;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAddressForm {

    private String street;

    private String city;

    private String country;

    private String postalCode;

    private String phoneNumber;

    private String email;
}

