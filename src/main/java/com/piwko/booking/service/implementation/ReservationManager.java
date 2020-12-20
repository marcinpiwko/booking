package com.piwko.booking.service.implementation;

import com.piwko.booking.persistence.EntityFactory;
import com.piwko.booking.persistence.model.Reservation;
import com.piwko.booking.persistence.model.User;
import com.piwko.booking.persistence.model.VirtualUser;
import com.piwko.booking.persistence.repository.ReservationRepository;
import com.piwko.booking.persistence.search.ReservationSearchCriteria;
import com.piwko.booking.service.interfaces.ReservationService;
import com.piwko.booking.service.interfaces.VirtualUserService;
import com.piwko.booking.util.DateTimeUtil;
import com.piwko.booking.util.StringUtil;
import com.piwko.booking.util.exception.ReservationException;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationManager implements ReservationService {

    private final ReservationRepository reservationRepository;

    private final VirtualUserService virtualUserService;

    private final ReservationValidator reservationValidator;

    private static final String ENTITY_NAME = EntityFactory.getEntityName(Reservation.class);

    @Override
    public Set<Long> getPendingReservationsToConfirm() {
        return reservationRepository.findPendingReservationsToConfirm();
    }

    @Override
    public void confirmPendingReservations(Set<Long> reservationIds) {
        reservationRepository.confirmPendingReservations(Reservation.Status.DONE, reservationIds);
    }

    @Override
    public Reservation getReservation(Long id, User user) throws ResourceNotFoundException {
        if (user.isCompanyUser()) {
            return reservationRepository.findByIdAndCompanyUser(id, user.getCompany().getCode())
                    .orElseThrow(() -> new ResourceNotFoundException(ENTITY_NAME, "id", id));
        } else {
            return reservationRepository.findByIdAndUser(id, user)
                    .orElseThrow(() -> new ResourceNotFoundException(ENTITY_NAME, "id", id));
        }
    }

    @Override
    public Page<Reservation> getReservations(ReservationSearchCriteria searchCriteria, User user, Pageable pageable) {
        if (!user.isCompanyUser()) {
            return reservationRepository.findAllByUser(user, pageable);
        } else {
            if (!StringUtil.isEmpty(searchCriteria.getLocationCode())) {
                if (!StringUtil.isEmpty(searchCriteria.getStatus())) {
                    return reservationRepository.findAllByLocationCodeAndStatus(searchCriteria.getLocationCode(), searchCriteria.getStatus(), pageable);
                }
                return reservationRepository.findAllByLocationCode(searchCriteria.getLocationCode(), pageable);
            }
            if (!StringUtil.isEmpty(searchCriteria.getStatus())) {
                return reservationRepository.findAllByStatus(searchCriteria.getStatus(), pageable);
            }
        }
        return reservationRepository.findAll(pageable);
    }

    @Override
    public Long book(Reservation reservation) throws ReservationException {
        reservationValidator.validateReservation(reservation);
        reservation.setStatus(Reservation.Status.PENDING);
        checkVirtualUser(reservation);
        return reservationRepository.save(reservation).getId();
    }

    private void checkVirtualUser(Reservation reservation) {
        if (reservation.getVirtualUser() != null) {
            try {
                VirtualUser virtualUser = virtualUserService.getVirtualUserByUsername(reservation.getVirtualUser().getEmail());
                if (!StringUtil.isEmpty(reservation.getVirtualUser().getPhoneNumber())) {
                    virtualUser.setPhoneNumber(reservation.getVirtualUser().getPhoneNumber());
                }
                if (reservation.getVirtualUser().getBirthDate() != null) {
                    virtualUser.setBirthDate(reservation.getVirtualUser().getBirthDate());
                }
                virtualUser.setFirstName(reservation.getVirtualUser().getFirstName());
                virtualUser.setLastName(reservation.getVirtualUser().getLastName());
                virtualUser.setGender(reservation.getVirtualUser().getGender());
                reservation.setVirtualUser(virtualUser);
            } catch (ResourceNotFoundException ignored) {}
        }
    }

    @Override
    public void cancel(Long reservationId, String reason, User user) throws ResourceNotFoundException, ReservationException {
        Reservation reservation = getReservation(reservationId, user);
        reservationValidator.validateCancellation(reservation);
        reservation.setCancellationDate(DateTimeUtil.getCurrentDate());
        reservation.setCancellationReason(reason);
        reservation.setStatus(Reservation.Status.CANCELLED);
        reservationRepository.save(reservation);
    }

    @Override
    public void mergeVirtualReservations(VirtualUser virtualUser, User user) {
        for (Reservation reservation : reservationRepository.findAllByVirtualUser(virtualUser)) {
            reservation.setVirtualUser(null);
            reservation.setUser(user);
            reservationRepository.save(reservation);
        }
    }
}
