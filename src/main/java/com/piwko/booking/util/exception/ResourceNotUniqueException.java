package com.piwko.booking.util.exception;

import com.piwko.booking.util.StringUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceNotUniqueException extends Exception {

    private static final String SCHEMA = "{0}'s {1}: {2} already exists";

    public ResourceNotUniqueException(Object... params) {
        super(StringUtil.replace(SCHEMA, params));
    }
}
