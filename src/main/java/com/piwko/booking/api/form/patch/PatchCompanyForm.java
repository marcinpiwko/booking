package com.piwko.booking.api.form.patch;

import lombok.Data;

import java.util.List;

@Data
public class PatchCompanyForm {

    private String code;

    private String name;

    private List<Long> specializationIds;

    private List<Long> locationIds;
}
