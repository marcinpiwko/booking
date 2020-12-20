package com.piwko.booking.util.exception;

import com.piwko.booking.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends Exception {

    private static final String SCHEMA = "Bad credentials for user {0}";

    public UnauthorizedException(Object... params) {
        super(StringUtil.replace(SCHEMA, params));
        log.error(this);
    }
}
