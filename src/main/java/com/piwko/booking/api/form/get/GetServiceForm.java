package com.piwko.booking.api.form.get;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetServiceForm {

    private Long id;

    private String code;

    private String name;

    private String description;

    private Double price;

    private boolean available;

    private Integer duration;

    private String companyCode;
}
