package com.piwko.booking.api;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "booking.page")
public class PageProperties {

    private Integer number;

    private Integer size;

    private String sortBy;
}
