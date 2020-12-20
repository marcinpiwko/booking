package com.piwko.booking.api.controller;

import com.piwko.booking.api.PageProperties;
import com.piwko.booking.api.form.common.IdResponse;
import com.piwko.booking.api.form.get.GetSpecializationForm;
import com.piwko.booking.api.form.get.GetSpecializationsForm;
import com.piwko.booking.api.form.patch.PatchSpecializationForm;
import com.piwko.booking.api.form.post.PostSpecializationForm;
import com.piwko.booking.api.swagger.SpecializationApi;
import com.piwko.booking.api.translator.SpecializationTranslator;
import com.piwko.booking.service.interfaces.SpecializationService;
import com.piwko.booking.util.exception.ResourceInUseException;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import com.piwko.booking.util.exception.ResourceNotUniqueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequiredArgsConstructor
public class SpecializationController implements SpecializationApi {

    private final SpecializationService specializationService;

    private final SpecializationTranslator specializationTranslator;

    private final PageProperties pageProperties;

    @Override
    public ResponseEntity<GetSpecializationForm> getSpecialization(Long id) throws ResourceNotFoundException {
        log.info("GET /api/specializations/" + id);
        return new ResponseEntity<>(specializationTranslator.translate(specializationService.getSpecialization(id)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetSpecializationsForm> getSpecializations() {
        log.info("GET /api/specializations");
        return new ResponseEntity<>(new GetSpecializationsForm(
                specializationService.getSpecializations(Sort.by(pageProperties.getSortBy()))
                        .stream().map(specializationTranslator::translate).collect(Collectors.toList())), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<IdResponse> createSpecialization(@Valid PostSpecializationForm postSpecializationForm) throws ResourceNotUniqueException {
        log.info("POST /api/specializations");
        IdResponse idResponse = new IdResponse(specializationService.createSpecialization(specializationTranslator.translate(postSpecializationForm)));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.LOCATION, "/api/specializations/" + idResponse.getId());
        return new ResponseEntity<>(idResponse, httpHeaders, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> modifySpecialization(Long id, @Valid PatchSpecializationForm patchSpecializationForm) throws ResourceNotFoundException, ResourceNotUniqueException {
        log.info("PATCH /api/specializations/" + id);
        specializationService.updateSpecialization(id, specializationTranslator.translate(patchSpecializationForm));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> deleteSpecialization(Long id) throws ResourceNotFoundException, ResourceInUseException {
        log.info("DELETE /api/specializations/" + id);
        specializationService.deleteSpecialization(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> refreshSpecializations() {
        log.info("POST /api/specializations/refresh");
        specializationService.refreshCache();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
