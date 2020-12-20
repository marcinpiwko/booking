package com.piwko.booking.api.form.patch;

import com.piwko.booking.api.form.interfaces.PostPatchCompany;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class PatchCompanyForm implements PostPatchCompany {

    @Size(min = 3, max = 10, message = "min code size = 3, max = 10")
    private String code;

    private String name;

    private Integer cancellationTime;

    private List<String> specializationCodes;
}
