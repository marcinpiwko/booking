package com.piwko.booking.persistence.cache;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "booking.cache")
public class CacheProperties {

    private Long maxTimeToLive;

    private Long maxTimeToIdle;

    private String memoryStorePolicy;

    private Integer maxEntries;

    private Boolean eternal;

}
