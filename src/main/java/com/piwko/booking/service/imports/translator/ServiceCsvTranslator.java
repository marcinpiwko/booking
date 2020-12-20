package com.piwko.booking.service.imports.translator;

import com.piwko.booking.persistence.EntityFactory;
import com.piwko.booking.persistence.model.Company;
import com.piwko.booking.persistence.model.Service;
import com.piwko.booking.service.imports.dto.ServiceCsv;
import com.piwko.booking.util.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
@Component
public class ServiceCsvTranslator {

    public Service translate(@Valid ServiceCsv serviceCsv, Company company) {
        Service service = EntityFactory.newEntityInstance(Service.class);
        service.setCode(serviceCsv.getCode());
        service.setName(serviceCsv.getName());
        service.setAvailable(serviceCsv.isAvailable());
        service.setDescription(StringUtil.getNullOrNonEmptyString(serviceCsv.getDescription()));
        service.setPrice(serviceCsv.getPrice());
        service.setDuration(serviceCsv.getDuration());
        service.setCompany(company);
        return service;
    }
}
