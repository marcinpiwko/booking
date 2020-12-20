package com.piwko.booking.util.exception;

import com.piwko.booking.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception {

    private static final String SCHEMA = "{0} with {1}: {2} not found";

    public ResourceNotFoundException(Object... params) {
        super(StringUtil.replace(SCHEMA, params));
        log.error(this);
    }
}
