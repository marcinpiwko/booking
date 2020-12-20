package com.piwko.booking.api.form.post;

import com.piwko.booking.api.form.interfaces.PostPatchAddress;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class PostAddressForm implements PostPatchAddress {

    @NotBlank(message = "street can not be empty")
    private String street;

    @NotBlank(message = "city can not be empty")
    private String city;

    @NotBlank(message = "country can not be empty")
    private String country;

    @Pattern(regexp = "[0-9]{2}-[0-9]{3}", message = "postalCode does not match the pattern eg. 32-425")
    @NotBlank(message = "postalCode can not be empty")
    private String postalCode;

    @Pattern(regexp = "[0-9]{9}", message = "phoneNumber does not match the pattern eg. 123123123")
    private String phoneNumber;

    @Email(message = "email does not match the pattern eg. test@test.com")
    private String email;

}
