package com.piwko.booking.api.translator;

import com.piwko.booking.api.form.get.GetCompanyForm;
import com.piwko.booking.api.form.interfaces.PostPatchCompany;
import com.piwko.booking.persistence.model.Company;
import com.piwko.booking.persistence.model.Location;
import com.piwko.booking.persistence.model.Service;
import com.piwko.booking.persistence.model.Specialization;
import com.piwko.booking.persistence.EntityFactory;
import com.piwko.booking.service.interfaces.SpecializationService;
import com.piwko.booking.util.CollectionsUtil;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompanyTranslator {

    private final SpecializationService specializationService;

    private final SpecializationTranslator specializationTranslator;

    public GetCompanyForm translate(Company company) {
        GetCompanyForm companyForm = new GetCompanyForm();
        companyForm.setId(company.getId());
        companyForm.setCode(company.getCode());
        companyForm.setName(company.getName());
        companyForm.setCancellationTime(company.getCancellationTime());
        if (!CollectionsUtil.isEmpty(company.getSpecializations())) {
            companyForm.setSpecializations(company.getSpecializations().stream().map(specializationTranslator::translate).collect(Collectors.toSet()));
        }
        if (!CollectionsUtil.isEmpty(company.getLocations())) {
            companyForm.setLocationCodes(company.getLocations().stream().map(Location::getCode).collect(Collectors.toSet()));
        }
        if (!CollectionsUtil.isEmpty(company.getServices())) {
            companyForm.setServiceCodes(company.getServices().stream().map(Service::getCode).collect(Collectors.toSet()));
        }
        return companyForm;
    }

    public Company translate(PostPatchCompany companyForm) throws ResourceNotFoundException {
        Company company = EntityFactory.newEntityInstance(Company.class);
        company.setCode(companyForm.getCode());
        company.setName(companyForm.getName());
        company.setCancellationTime(companyForm.getCancellationTime());
        if (!CollectionsUtil.isEmpty(companyForm.getSpecializationCodes())) {
            Set<Specialization> specializations = new HashSet<>();
            for (String specCode : companyForm.getSpecializationCodes()) {
                specializations.add(specializationService.getSpecialization(specCode));
            }
            company.setSpecializations(specializations);
        } else {
            company.setSpecializations(Collections.emptySet());
        }
        return company;
    }
}
