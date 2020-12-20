package com.piwko.booking.service.interfaces;

import com.piwko.booking.persistence.model.Reservation;
import com.piwko.booking.persistence.model.User;
import com.piwko.booking.persistence.model.VirtualUser;
import com.piwko.booking.persistence.search.ReservationSearchCriteria;
import com.piwko.booking.util.exception.ReservationException;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface ReservationService {

    Set<Long> getPendingReservationsToConfirm();

    void confirmPendingReservations(Set<Long> reservationIds);

    Reservation getReservation(Long id, User user) throws ResourceNotFoundException;

    Page<Reservation> getReservations(ReservationSearchCriteria searchCriteria, User user, Pageable pageable);

    Long book(Reservation reservation) throws ReservationException;

    void cancel(Long reservationId, String reason, User user) throws ResourceNotFoundException, ReservationException;

    void mergeVirtualReservations(VirtualUser virtualUser, User user);
}
