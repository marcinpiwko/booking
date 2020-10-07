package com.piwko.booking.api.form.get;

import lombok.Data;
import java.util.List;

@Data
public class GetCompanyForm {

    private Long id;

    private String code;

    private String name;

    private List<GetSpecializationForm> specializations;

    private List<GetLocationForm> locations;
}
