package com.piwko.booking.api.form.get;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GetUserForm {

    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    private String gender;

    private String companyCode;

    private String phoneNumber;

    private LocalDate birthDate;
}
