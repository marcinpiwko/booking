package com.piwko.booking.api.controller;

import com.piwko.booking.api.form.common.IdResponse;
import com.piwko.booking.api.form.get.GetCompaniesForm;
import com.piwko.booking.api.form.get.GetCompanyForm;
import com.piwko.booking.api.form.patch.PatchCompanyForm;
import com.piwko.booking.api.form.post.PostCompanyForm;
import com.piwko.booking.api.swagger.CompanyApi;
import com.piwko.booking.api.translator.CompanyTranslator;
import com.piwko.booking.service.interfaces.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CompanyController implements CompanyApi {

    private final CompanyService companyService;

    private final CompanyTranslator companyTranslator;

    private final PageProperties pageProperties;

    @SneakyThrows
    @Override
    public ResponseEntity<GetCompanyForm> getCompany(Long id) {
        return new ResponseEntity<>(companyTranslator.translate(companyService.getCompany(id)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetCompaniesForm> getCompanies(Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy) {
        return new ResponseEntity<>(new GetCompaniesForm(
                companyService.getCompanies(PageRequest.of(page.orElse(pageProperties.getNumber()), size.orElse(pageProperties.getSize()), Sort.by(sortBy.orElse(pageProperties.getSortBy()))))
                        .map(companyTranslator::translate)), HttpStatus.OK);
    }

    @SneakyThrows
    @Override
    public ResponseEntity<IdResponse> createCompany(PostCompanyForm postCompanyForm) {
        return new ResponseEntity<>(new IdResponse(companyService.createCompany(companyTranslator.translate(postCompanyForm))), HttpStatus.CREATED);
    }

    @SneakyThrows
    @Override
    public ResponseEntity<Void> modifyCompany(Long id, PatchCompanyForm patchCompanyForm) {
        companyService.modifyCompany(id, companyTranslator.translate(patchCompanyForm));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> deleteCompany(Long id) {
        companyService.deleteCompany(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
