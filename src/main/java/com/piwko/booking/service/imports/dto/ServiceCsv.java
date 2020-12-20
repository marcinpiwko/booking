package com.piwko.booking.service.imports.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ServiceCsv {

    @NotBlank(message = "code can not be empty")
    @Size(min = 3, max = 10, message = "min code size = 3, max = 10")
    private String code;

    @NotBlank(message = "name can not be empty")
    private String name;

    private String description;

    @NotNull(message = "price can not be empty")
    private Double price;

    @NotNull(message = "duration can not be empty")
    private Integer duration;

    private boolean available = true;
}
