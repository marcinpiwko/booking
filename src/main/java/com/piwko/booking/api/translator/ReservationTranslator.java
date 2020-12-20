package com.piwko.booking.api.translator;

import com.piwko.booking.api.form.common.reservation.ReservationRequest;
import com.piwko.booking.api.form.get.GetReservationForm;
import com.piwko.booking.persistence.EntityFactory;
import com.piwko.booking.persistence.model.Reservation;
import com.piwko.booking.service.interfaces.EmployeeService;
import com.piwko.booking.service.interfaces.LocationService;
import com.piwko.booking.service.interfaces.ServiceService;
import com.piwko.booking.service.interfaces.UserService;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Data
@Component
@RequiredArgsConstructor
public class ReservationTranslator {

    private final UserService userService;

    private final EmployeeService employeeService;

    private final LocationService locationService;

    private final ServiceService serviceService;

    private final VirtualUserTranslator virtualUserTranslator;

    public Reservation translate(ReservationRequest reservationRequest, Optional<String> username) throws ResourceNotFoundException {
        Reservation reservation = EntityFactory.newEntityInstance(Reservation.class);
        reservation.setReservationDate(reservationRequest.getDate());
        if (username.isPresent()) {
            reservation.setUser(userService.getUser(username.get()));
        } else {
            reservation.setVirtualUser(virtualUserTranslator.translate(reservationRequest.getUser()));
        }
        reservation.setLocation(locationService.getLocation(reservationRequest.getLocationCode()));
        reservation.setEmployee(employeeService.getEmployee(reservationRequest.getEmployeeCode()));
        reservation.setService(serviceService.getService(reservationRequest.getServiceCode()));
        return reservation;
    }

    public GetReservationForm translate(Reservation reservation) {
        GetReservationForm reservationForm = new GetReservationForm();
        reservationForm.setCancellationDate(reservation.getCancellationDate());
        reservationForm.setCancellationReason(reservation.getCancellationReason());
        reservationForm.setEmployee(reservation.getEmployee().getName());
        reservationForm.setDate(reservation.getReservationDate());
        reservationForm.setServiceCode(reservation.getService().getCode());
        reservationForm.setStatus(reservation.getStatus());
        reservationForm.setCreationDate(reservation.getCreationDate());
        reservationForm.setLocationCode(reservation.getLocation().getCode());
        if (reservation.getUser() != null) {
            reservationForm.setUsername(reservation.getUser().getEmail());
        } else {
            reservationForm.setUsername(reservation.getVirtualUser().getEmail());
            reservationForm.setVirtual(true);
        }
        return reservationForm;
    }
}
