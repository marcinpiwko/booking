package com.piwko.booking.util.exception;

import com.piwko.booking.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceInvalidStatusException extends Exception {

    private static final String SCHEMA = "{0} with id: {1} invalid status {2}";

    public ResourceInvalidStatusException(Object... params) {
        super(StringUtil.replace(SCHEMA, params));
        log.error(this);
    }
}
