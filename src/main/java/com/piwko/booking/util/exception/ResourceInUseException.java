package com.piwko.booking.util.exception;

import com.piwko.booking.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceInUseException extends Exception {

    private static final String SCHEMA = "Can't delete {0} with id: {1}, is in use";

    public ResourceInUseException(Object... params) {
        super(StringUtil.replace(SCHEMA, params));
        log.error(this);
    }
}
