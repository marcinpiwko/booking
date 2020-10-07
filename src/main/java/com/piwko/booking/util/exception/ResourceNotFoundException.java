package com.piwko.booking.util.exception;

import com.piwko.booking.util.StringUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception {

    private static final String SCHEMA = "{0} with id: {1} not found";

    public ResourceNotFoundException(Object... params) {
        super(StringUtil.replace(SCHEMA, params));
    }
}
