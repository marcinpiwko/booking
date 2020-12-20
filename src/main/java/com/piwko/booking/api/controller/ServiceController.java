package com.piwko.booking.api.controller;

import com.piwko.booking.api.PageProperties;
import com.piwko.booking.api.form.common.IdResponse;
import com.piwko.booking.api.form.get.GetServiceForm;
import com.piwko.booking.api.form.get.GetServicesForm;
import com.piwko.booking.api.form.patch.PatchServiceForm;
import com.piwko.booking.api.form.post.PostServiceForm;
import com.piwko.booking.api.swagger.ServiceApi;
import com.piwko.booking.api.translator.ServiceTranslator;
import com.piwko.booking.persistence.model.User;
import com.piwko.booking.persistence.search.ServiceSearchCriteria;
import com.piwko.booking.service.interfaces.ServiceService;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;
import java.util.Optional;

@Log4j2
@RestController
@RequiredArgsConstructor
public class ServiceController implements ServiceApi {

    private final ServiceService serviceService;

    private final ServiceTranslator serviceTranslator;

    private final UserService userService;

    private final PageProperties pageProperties;

    @Override
    public ResponseEntity<GetServiceForm> getService(Long id) throws ResourceNotFoundException {
        log.info("GET /api/services/" + id);
        return new ResponseEntity<>(serviceTranslator.translate(serviceService.getService(id)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetServicesForm> getServices(Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy, Optional<String> companyCode, Optional<String> locationCode, Optional<String> employee) {
        log.info("GET /api/services");
        return new ResponseEntity<>(new GetServicesForm(serviceService.getServices(
                new ServiceSearchCriteria(companyCode.orElse(null), locationCode.orElse(null), employee.orElse(null)),
                PageRequest.of(page.orElse(pageProperties.getNumber()), size.orElse(pageProperties.getSize()), sortBy.map(Sort::by).orElseGet(Sort::unsorted)))
                .map(serviceTranslator::translate)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<IdResponse> createService(@Valid PostServiceForm postServiceForm) throws ResourceNotUniqueException, ResourceNotFoundException {
        log.info("POST /api/services");
        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        IdResponse idResponse = new IdResponse(serviceService.createService(serviceTranslator.translate(postServiceForm, Optional.of(user))));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.LOCATION, "/api/services/" + idResponse.getId());
        return new ResponseEntity<>(idResponse, httpHeaders, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> modifyService(Long id, @Valid PatchServiceForm patchServiceForm) throws ResourceNotFoundException, ResourceNotUniqueException, AccessDeniedException {
        log.info("PATCH /api/services/" + id);
        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!serviceService.existsByCompany(user.getCompany().getId())) {
            throw new AccessDeniedException("You don't have permission to modify service with id " + id);
        }
        serviceService.updateService(id, serviceTranslator.translate(patchServiceForm, Optional.empty()));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> deleteService(Long id) throws ResourceNotFoundException, AccessDeniedException {
        log.info("DELETE /api/services/" + id);
        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!serviceService.existsByCompany(user.getCompany().getId())) {
            throw new AccessDeniedException("You don't have permission to delete service with id " + id);
        }
        serviceService.deleteService(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> refreshServices() {
        log.info("POST /api/services/refresh");
        serviceService.refreshCache();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
