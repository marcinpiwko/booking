package com.piwko.booking.api.form.post;

import com.piwko.booking.api.form.interfaces.PostPatchCompany;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class PostCompanyForm implements PostPatchCompany {

    @NotBlank(message = "code can not be empty")
    @Size(min = 3, max = 10, message = "min code size = 3, max = 10")
    private String code;

    @NotBlank(message = "name can not be empty")
    private String name;

    @NotNull(message = "cancellationTime can not be null")
    private Integer cancellationTime;

    @NotEmpty(message = "specializationCodes can not be empty")
    private List<String> specializationCodes;
}
