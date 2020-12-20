package com.piwko.booking.api.form.common.authentication;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AuthenticationRequest {

    @Schema(description = "email as username", example = "test@test.com")
    @NotBlank(message = "email can not be empty")
    private String email;

    @Schema(description = "user's password")
    @NotBlank(message = "password can not be empty")
    private String password;

    @Schema(description = "for long token expiration time")
    private boolean rememberMe;
}
