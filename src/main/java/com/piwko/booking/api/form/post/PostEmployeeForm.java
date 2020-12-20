package com.piwko.booking.api.form.post;

import com.piwko.booking.api.form.interfaces.PostPatchEmployee;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class PostEmployeeForm implements PostPatchEmployee {

    @NotBlank(message = "firstName can not be empty")
    private String firstName;

    @NotBlank(message = "lastName can not be empty")
    private String lastName;

    @Size(min = 3, max = 10, message = "min code size = 3, max = 10")
    @NotBlank(message = "employeeCode can not be empty")
    private String code;

    @Size(min = 3, max = 10, message = "min code size = 3, max = 10")
    @NotBlank(message = "locationCode can not be empty")
    private String locationCode;

    @NotNull(message = "workingHours can not be null")
    @Valid
    private PostWorkingHoursForm workingHours;

    @NotEmpty(message = "serviceCodes can not be empty")
    private List<String> serviceCodes;
}
