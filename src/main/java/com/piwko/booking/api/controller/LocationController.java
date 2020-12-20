package com.piwko.booking.api.controller;

import com.piwko.booking.api.PageProperties;
import com.piwko.booking.api.form.common.IdResponse;
import com.piwko.booking.api.form.get.GetLocationForm;
import com.piwko.booking.api.form.get.GetLocationsForm;
import com.piwko.booking.api.form.patch.PatchLocationForm;
import com.piwko.booking.api.form.post.PostLocationForm;
import com.piwko.booking.api.swagger.LocationApi;
import com.piwko.booking.api.translator.LocationTranslator;
import com.piwko.booking.persistence.model.User;
import com.piwko.booking.persistence.search.LocationSearchCriteria;
import com.piwko.booking.service.interfaces.LocationService;
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
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@Log4j2
@RestController
@RequiredArgsConstructor
public class LocationController implements LocationApi {

    private final LocationService locationService;

    private final LocationTranslator locationTranslator;

    private final UserService userService;

    private final PageProperties pageProperties;

    @Override
    public ResponseEntity<GetLocationForm> getLocation(Long id) throws ResourceNotFoundException {
        log.info("GET /api/locations/" + id);
        return new ResponseEntity<>(locationTranslator.translate(locationService.getLocation(id)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetLocationsForm> getLocations(Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy, Optional<String> companyCode) {
        log.info("GET /api/locations");
        return new ResponseEntity<>(new GetLocationsForm(
                locationService.getLocations(new LocationSearchCriteria(companyCode.orElse(null)), PageRequest.of(page.orElse(pageProperties.getNumber()), size.orElse(pageProperties.getSize()), sortBy.map(Sort::by).orElseGet(Sort::unsorted)))
                        .map(locationTranslator::translate)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<IdResponse> createLocation(@Valid PostLocationForm postLocationForm) throws ResourceNotUniqueException, ResourceNotFoundException {
        log.info("POST /api/locations");
        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        IdResponse idResponse = new IdResponse(locationService.createLocation(locationTranslator.translate(postLocationForm, Optional.of(user))));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.LOCATION, "/api/locations/" + idResponse.getId());
        return new ResponseEntity<>(idResponse, httpHeaders, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> modifyLocation(Long id, @Valid PatchLocationForm patchLocationForm) throws ResourceNotFoundException, ResourceNotUniqueException {
        log.info("PATCH /api/locations/" + id);
        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!locationService.existsByCompany(id, user.getCompany().getId())) {
            throw new AccessDeniedException("You don't have permission to modify location with id " + id);
        }
        locationService.updateLocation(id, locationTranslator.translate(patchLocationForm, Optional.empty()));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> deleteLocation(Long id) throws ResourceNotFoundException {
        log.info("DELETE /api/locations/" + id);
        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!locationService.existsByCompany(id, user.getCompany().getId())) {
            throw new AccessDeniedException("You don't have permission to delete location with id " + id);
        }
        locationService.deleteLocation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> refreshLocations() {
        log.info("POST /api/locations/refresh");
        locationService.refreshCache();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
