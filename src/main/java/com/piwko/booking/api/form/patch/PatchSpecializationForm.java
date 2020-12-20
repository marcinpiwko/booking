package com.piwko.booking.api.form.patch;

import com.piwko.booking.api.form.interfaces.PostPatchSpecialization;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class PatchSpecializationForm implements PostPatchSpecialization {

    @Size(min = 3, max = 10, message = "min code size = 3, max = 10")
    private String code;

    private String name;
}
