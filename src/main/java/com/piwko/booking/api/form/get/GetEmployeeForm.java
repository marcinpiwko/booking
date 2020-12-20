package com.piwko.booking.api.form.get;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetEmployeeForm {

    private Long id;

    private String firstName;

    private String lastName;

    private String employeeCode;

    private String locationCode;

    private List<String> serviceCodes;

    private GetWorkingHoursForm workingHours;
}
