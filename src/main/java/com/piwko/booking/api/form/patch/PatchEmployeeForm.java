package com.piwko.booking.api.form.patch;

import com.piwko.booking.api.form.interfaces.PostPatchEmployee;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class PatchEmployeeForm implements PostPatchEmployee {

    private String firstName;

    private String lastName;

    @Size(min = 3, max = 10, message = "min code size = 3, max = 10")
    private String code;

    @Size(min = 3, max = 10, message = "min code size = 3, max = 10")
    private String locationCode;

    private PatchWorkingHoursForm workingHours;

    private List<String> serviceCodes;
}
