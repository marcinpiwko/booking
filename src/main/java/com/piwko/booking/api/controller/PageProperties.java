package com.piwko.booking.api.controller;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "booking.page")
public class PageProperties {

    private Integer number;

    private Integer size;

    private String sortBy;
}
