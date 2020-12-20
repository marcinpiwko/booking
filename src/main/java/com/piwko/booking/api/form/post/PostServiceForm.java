package com.piwko.booking.api.form.post;

import com.piwko.booking.api.form.interfaces.PostPatchService;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PostServiceForm implements PostPatchService {

    @NotBlank(message = "code can not be blank")
    @Size(min = 3, max = 10, message = "min code size = 3, max = 10")
    private String code;

    @NotBlank(message = "name can not be blank")
    private String name;

    private String description;

    private Double price;

    private boolean available = true;

    @NotNull(message = "duration can not be null")
    private Integer duration;
}
