package com.piwko.booking.service.imports.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class EmployeeCsv {

    @NotBlank(message = "firstName can not be empty")
    private String firstName;

    @NotBlank(message = "lastName can not be empty")
    private String lastName;

    @NotBlank(message = "employeeCode can not be empty")
    @Size(min = 3, max = 10, message = "min code size = 3, max = 10")
    private String code;

    @NotBlank(message = "locationCode can not be empty")
    private String locationCode;

    @NotEmpty(message = "serviceCodes can not be empty")
    private List<String> serviceCodes;

    @NotEmpty(message = "workingHours can not be empty")
    private List<String> workingHours;

}
