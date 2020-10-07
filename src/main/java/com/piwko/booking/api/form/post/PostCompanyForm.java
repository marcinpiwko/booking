package com.piwko.booking.api.form.post;

import lombok.Data;

import java.util.List;

@Data
public class PostCompanyForm {

    private String code;

    private String name;

    private List<Long> specializationIds;

    private List<Long> locationIds;
}
