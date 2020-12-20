package com.piwko.booking.api.controller;

import com.piwko.booking.api.PageProperties;
import com.piwko.booking.api.form.common.IdResponse;
import com.piwko.booking.api.form.get.GetCompaniesForm;
import com.piwko.booking.api.form.get.GetCompanyForm;
import com.piwko.booking.api.form.patch.PatchCompanyForm;
import com.piwko.booking.api.form.post.PostCompanyForm;
import com.piwko.booking.api.swagger.CompanyApi;
import com.piwko.booking.api.translator.CompanyTranslator;
import com.piwko.booking.persistence.model.Role;
import com.piwko.booking.persistence.model.User;
import com.piwko.booking.persistence.search.CompanySearchCriteria;
import com.piwko.booking.service.interfaces.CompanyService;
import com.piwko.booking.service.interfaces.UserService;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import com.piwko.booking.util.exception.ResourceNotUniqueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@Log4j2
@RestController
@RequiredArgsConstructor
public class CompanyController implements CompanyApi {

    private final CompanyService companyService;

    private final CompanyTranslator companyTranslator;

    private final UserService userService;

    private final PageProperties pageProperties;

    @Override
    public ResponseEntity<GetCompanyForm> getCompany(Long id) throws ResourceNotFoundException {
        log.info("GET /api/companies/" + id);
        return new ResponseEntity<>(companyTranslator.translate(companyService.getCompany(id)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetCompaniesForm> getCompanies(Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy, Optional<String> city, Optional<String> specializationCode) {
        log.info("GET /api/companies");
        return new ResponseEntity<>(new GetCompaniesForm(
                companyService.getCompanies(new CompanySearchCriteria(specializationCode.orElse(null), city.orElse(null)),
                        PageRequest.of(page.orElse(pageProperties.getNumber()), size.orElse(pageProperties.getSize()), sortBy.map(Sort::by).orElseGet(Sort::unsorted)))
                        .map(companyTranslator::translate)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<IdResponse> createCompany(@Valid @RequestBody PostCompanyForm postCompanyForm) throws ResourceNotUniqueException, ResourceNotFoundException {
        log.info("POST /api/companies");
        IdResponse idResponse = new IdResponse(companyService.createCompany(companyTranslator.translate(postCompanyForm)));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.LOCATION, "/api/companies/" + idResponse.getId());
        return new ResponseEntity<>(idResponse, httpHeaders, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> modifyCompany(Long id, @Valid PatchCompanyForm patchCompanyForm) throws ResourceNotFoundException, ResourceNotUniqueException {
        log.info("PATCH /api/companies/" + id);
        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!Role.RoleType.ADMIN.name().equals(user.getRole().getName()) && !id.equals(user.getCompany().getId())) {
            throw new AccessDeniedException("You don't have permission to modify company with id " + id);
        }
        companyService.updateCompany(id, companyTranslator.translate(patchCompanyForm));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> deleteCompany(Long id) throws ResourceNotFoundException {
        log.info("DELETE /api/companies/" + id);
        companyService.deleteCompany(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> refreshCompanies() {
        log.info("POST /api/companies/refresh");
        companyService.refreshCache();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
