package com.piwko.booking.api.controller;

import com.piwko.booking.api.PageProperties;
import com.piwko.booking.api.form.common.IdResponse;
import com.piwko.booking.api.form.common.reservation.CancellationRequest;
import com.piwko.booking.api.form.common.reservation.ReservationRequest;
import com.piwko.booking.api.form.get.GetReservationForm;
import com.piwko.booking.api.form.get.GetReservationsForm;
import com.piwko.booking.api.swagger.ReservationApi;
import com.piwko.booking.api.translator.ReservationTranslator;
import com.piwko.booking.persistence.model.User;
import com.piwko.booking.persistence.search.ReservationSearchCriteria;
import com.piwko.booking.service.interfaces.LocationService;
import com.piwko.booking.service.interfaces.ReservationService;
import com.piwko.booking.service.interfaces.UserService;
import com.piwko.booking.util.exception.ReservationException;
import com.piwko.booking.util.exception.ResourceNotFoundException;
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
public class ReservationController implements ReservationApi {

    private final ReservationService reservationService;

    private final UserService userService;

    private final LocationService locationService;

    private final ReservationTranslator reservationTranslator;

    private final PageProperties pageProperties;

    @Override
    public ResponseEntity<IdResponse> createReservation(@Valid ReservationRequest request) throws ReservationException, ResourceNotFoundException {
        log.info("POST /api/reservations");
        IdResponse idResponse;
        if (!"anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            idResponse = new IdResponse(reservationService.book(reservationTranslator.translate(request, Optional.of(SecurityContextHolder.getContext().getAuthentication().getName()))));
        } else {
            idResponse = new IdResponse(reservationService.book(reservationTranslator.translate(request, Optional.empty())));
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.LOCATION, "/api/reservations/" + idResponse.getId());
        return new ResponseEntity<>(idResponse, httpHeaders, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<GetReservationForm> getReservation(Long id) throws ResourceNotFoundException {
        log.info("GET /api/reservations/" + id);
        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        return new ResponseEntity<>(reservationTranslator.translate(reservationService.getReservation(id, user)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetReservationsForm> getReservations(Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy, Optional<String> locationCode, Optional<String> status) throws ResourceNotFoundException {
        log.info("GET /api/reservations");
        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        if (locationCode.isPresent() && !locationService.existsByCompany(locationCode.get(), user.getCompany().getCode())) {
            throw new AccessDeniedException("Location " + locationCode + " does not belong to your company");
        }
        return new ResponseEntity<>(new GetReservationsForm(reservationService.getReservations(
                new ReservationSearchCriteria(locationCode.orElse(null), status.orElse(null)), user, PageRequest.of(page.orElse(pageProperties.getNumber()), size.orElse(pageProperties.getSize()), sortBy.map(Sort::by).orElseGet(Sort::unsorted)))
                    .map(reservationTranslator::translate)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> cancelReservation(Long id, @Valid CancellationRequest cancellationRequest) throws ResourceNotFoundException, ReservationException {
        log.info("PATCH /api/reservations/" + id + "/cancel");
        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        reservationService.cancel(id, cancellationRequest.getReason(), user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
