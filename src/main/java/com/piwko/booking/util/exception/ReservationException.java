package com.piwko.booking.util.exception;

import com.piwko.booking.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ReservationException extends Exception {

    public interface Message {
        String CANCELLATION_INVALID_STATUS = "Reservation with id: {0} can not be cancelled due to invalid status: {1}";
        String CANCELLATION_TIME_EXPIRED = "Reservation with id: {0} can not be cancelled due to company cancellation time: {1} hours";
        String RESERVATION_INVALID_DATE = "Reservation date: {0} before current date";
        String SERVICE_UNAVAILABLE = "Reservation service: {0} currently unavailable";
        String EMPLOYEE_UNAVAILABLE = "Reservation employee: {0} unavailable at time: {1}";
        String LOCATION_UNAVAILABLE = "Reservation location: {0} unavailable at time: {1}";
        String RESERVATION_DATE_SERVICE = "Reservation date: {0} and service duration: {1} invalid due to employee end working hour";
        String EMPLOYEE_WITHOUT_SERVICE = "Reservation employee: {0} invalid service: {1}";
        String EMPLOYEE_IN_LOCATION_NOT_FOUND = "Reservation employee: {0} not found in location: {1}";
    }

    public ReservationException(String message, Object... params) {
        super(StringUtil.replace(message, params));
        log.error(this);
    }
}
