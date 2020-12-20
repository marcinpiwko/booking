package com.piwko.booking.api.form.post;

import com.piwko.booking.api.form.interfaces.PostPatchSpecialization;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PostSpecializationForm implements PostPatchSpecialization {

    @NotBlank(message = "code can not be blank")
    @Size(min = 3, max = 10, message = "min code size = 3, max = 10")
    private String code;

    @NotBlank(message = "name can not be blank")
    private String name;
}
