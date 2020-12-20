package com.piwko.booking.api.form.patch;

import com.piwko.booking.api.form.interfaces.PostPatchLocation;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class PatchLocationForm implements PostPatchLocation {

    @Size(min = 3, max = 10, message = "min code size = 3, max = 10")
    private String code;

    private String name;

    private PatchAddressForm address;

    private PatchWorkingHoursForm workingHours;

}
