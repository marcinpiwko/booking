package com.piwko.booking.api.form.patch;

import com.piwko.booking.api.form.interfaces.PostPatchAddress;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class PatchAddressForm implements PostPatchAddress {

    private String street;

    private String city;

    private String country;

    @Pattern(regexp = "[0-9]{2}-[0-9]{3}", message = "postalCode does not match the pattern eg. 32-425")
    private String postalCode;

    @Pattern(regexp = "[0-9]{9}", message = "phoneNumber does not match the pattern eg. 123123123")
    private String phoneNumber;

    @Email(message = "email does not match the pattern eg. test@test.com")
    private String email;
}
