package com.piwko.booking.api.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "booking.security")
@Component
@Getter
@Setter
public class JwtProperties {

    private String tokenPrefix;

    private Long validity;

    private String secretKey;

    private String headerName = HttpHeaders.AUTHORIZATION;

    private int rememberMe;

}
