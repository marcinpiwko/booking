package com.piwko.booking.api.translator;

import com.piwko.booking.api.form.get.GetCompanyForm;
import com.piwko.booking.api.form.patch.PatchCompanyForm;
import com.piwko.booking.api.form.post.PostCompanyForm;
import com.piwko.booking.persistence.model.Company;
import com.piwko.booking.persistence.model.Location;
import com.piwko.booking.persistence.model.Specialization;
import com.piwko.booking.persistence.EntityFactory;
import com.piwko.booking.service.interfaces.LocationService;
import com.piwko.booking.service.interfaces.SpecializationService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompanyTranslator {

    private final SpecializationService specializationService;

    private final LocationService locationService;

    private final SpecializationTranslator specializationTranslator;

    private final LocationTranslator locationTranslator;

    public GetCompanyForm translate(Company company) {
        GetCompanyForm companyForm = new GetCompanyForm();
        companyForm.setId(company.getId());
        companyForm.setCode(company.getCode());
        companyForm.setName(company.getName());
        companyForm.setLocations(company.getLocations().stream().map(locationTranslator::translate).collect(Collectors.toList()));
        companyForm.setSpecializations(company.getSpecializations().stream().map(specializationTranslator::translate).collect(Collectors.toList()));
        return companyForm;
    }

    public Company translate(PostCompanyForm postCompanyForm) {
        Company company = EntityFactory.newEntityInstance(Company.class);
        company.setCode(postCompanyForm.getCode());
        company.setName(postCompanyForm.getName());
        company.setLocations(postCompanyForm.getLocationIds().stream().map(this::getLocation).collect(Collectors.toList()));
        company.setSpecializations(postCompanyForm.getSpecializationIds().stream().map(this::getSpecialization).collect(Collectors.toList()));
        return company;
    }

    public Company translate(PatchCompanyForm patchCompanyForm) {
        Company company = EntityFactory.newEntityInstance(Company.class);
        company.setCode(patchCompanyForm.getCode());
        company.setName(patchCompanyForm.getName());
        company.setLocations(patchCompanyForm.getLocationIds().stream().map(this::getLocation).collect(Collectors.toList()));
        company.setSpecializations(patchCompanyForm.getSpecializationIds().stream().map(this::getSpecialization).collect(Collectors.toList()));
        return company;
    }

    @SneakyThrows
    private Specialization getSpecialization(Long id) {
        return specializationService.getSpecialization(id);
    }

    @SneakyThrows
    private Location getLocation(Long id) {
        return locationService.getLocation(id);
    }
}
