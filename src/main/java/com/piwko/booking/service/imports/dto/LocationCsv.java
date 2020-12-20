package com.piwko.booking.service.imports.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.util.List;

@Data
public class LocationCsv {

    @NotBlank(message = "code can not be empty")
    @Size(min = 3, max = 10, message = "min code size = 3, max = 10")
    private String code;

    @NotBlank(message = "name can not be empty")
    private String name;

    private String company;

    @NotBlank(message = "street can not be empty")
    private String street;

    @NotBlank(message = "city can not be empty")
    private String city;

    @NotBlank(message = "country can not be empty")
    private String country;

    @Pattern(regexp = "[0-9]{2}-[0-9]{3}", message = "postalCode does not match the pattern eg. 32-425")
    @NotBlank(message = "postalCode can not be empty")
    private String postalCode;

    @Pattern(regexp = "^$|[0-9]{9}", message = "phoneNumber does not match the pattern eg. 123123123")
    private String phoneNumber;

    @Email(message = "email does not match the pattern eg. test@test.com")
    private String email;

    @NotEmpty(message = "workingHours can not be empty")
    private List<String> workingHours;
}
