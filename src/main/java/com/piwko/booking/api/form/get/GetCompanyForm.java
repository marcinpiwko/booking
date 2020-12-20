package com.piwko.booking.api.form.get;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class GetCompanyForm {

    private Long id;

    private String code;

    private String name;

    private Integer cancellationTime;

    private Set<GetSpecializationForm> specializations;

    private Set<String> locationCodes;

    private Set<String> serviceCodes;
}
