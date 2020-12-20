package com.piwko.booking.service.implementation;

import com.piwko.booking.persistence.model.Reservation;
import com.piwko.booking.persistence.repository.EmployeeRepository;
import com.piwko.booking.persistence.repository.ReservationRepository;
import com.piwko.booking.util.DateTimeUtil;
import com.piwko.booking.util.WorkingHoursUtil;
import com.piwko.booking.util.exception.ReservationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
class ReservationValidator {

    private final ReservationRepository reservationRepository;

    private final EmployeeRepository employeeRepository;

    public void validateCancellation(Reservation reservation) throws ReservationException {
        if (!Reservation.Status.PENDING.equals(reservation.getStatus())) {
            throw new ReservationException(ReservationException.Message.CANCELLATION_INVALID_STATUS, reservation.getId(), Reservation.Status.PENDING);
        }
        if (DateTimeUtil.getCurrentDate().isAfter(reservation.getReservationDate().minusHours(reservation.getLocation().getCompany().getCancellationTime()))) {
            throw new ReservationException(ReservationException.Message.CANCELLATION_TIME_EXPIRED, reservation.getId(), reservation.getLocation().getCompany().getCancellationTime());
        }
    }

    public void validateReservation(Reservation reservation) throws ReservationException {
        // check if service is available at the moment
        if (!reservation.getService().isAvailable()) {
            throw new ReservationException(ReservationException.Message.SERVICE_UNAVAILABLE, reservation.getService().getCode());
        }
        // check if employee belongs to location
        if (!employeeRepository.existsByLocation(reservation.getEmployee().getId(), reservation.getLocation().getId())) {
            throw new ReservationException(ReservationException.Message.EMPLOYEE_IN_LOCATION_NOT_FOUND, reservation.getEmployee().getName(), reservation.getLocation().getCompany());
        }
        // check if service belongs to employee
        if (!reservation.getEmployee().getServices().contains(reservation.getService())) {
            throw new ReservationException(ReservationException.Message.EMPLOYEE_WITHOUT_SERVICE, reservation.getEmployee().getName(), reservation.getService().getCode());
        }
        // check if reservation date is not before current date
        if (reservation.getReservationDate().isBefore(DateTimeUtil.getCurrentDate())) {
            throw new ReservationException(ReservationException.Message.RESERVATION_INVALID_DATE, reservation.getReservationDate());
        }
        // check if reservation date is within employee working hours
        if (!WorkingHoursUtil.isWithinWorkingHours(reservation.getReservationDate(), reservation.getEmployee().getWorkingHours())) {
            throw new ReservationException(ReservationException.Message.EMPLOYEE_UNAVAILABLE, reservation.getEmployee().getName(), reservation.getReservationDate());
        }
        // check if reservation date + service duration is free time for employee
        if (!reservationRepository.isDateFreeForReservation(reservation.getReservationDate(), reservation.getReservationDate().plusMinutes(reservation.getService().getDuration()), reservation.getEmployee().getId())) {
            throw new ReservationException(ReservationException.Message.EMPLOYEE_UNAVAILABLE, reservation.getEmployee().getName(), reservation.getReservationDate());
        }
        // check if reservation date + service duration is not after employee end working hour
        LocalTime reservationTime = reservation.getReservationDate().plusMinutes(reservation.getService().getDuration()).toLocalTime();
        if (reservationTime.isAfter(reservation.getEmployee().getWorkingHours().getWorkingHoursByDayOfWeek().get(reservation.getReservationDate().getDayOfWeek()).getTo())) {
            throw new ReservationException(ReservationException.Message.RESERVATION_DATE_SERVICE, reservation.getReservationDate(), reservation.getService().getDuration());
        }
    }
}
