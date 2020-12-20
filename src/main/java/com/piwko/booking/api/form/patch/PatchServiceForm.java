package com.piwko.booking.api.form.patch;

import com.piwko.booking.api.form.interfaces.PostPatchService;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class PatchServiceForm implements PostPatchService {

    @Size(min = 3, max = 10, message = "min code size = 3, max = 10")
    private String code;

    private String name;

    private String description;

    private Double price;

    private boolean available = true;

    private Integer duration;

}
