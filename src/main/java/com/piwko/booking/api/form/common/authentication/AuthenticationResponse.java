package com.piwko.booking.api.form.common.authentication;

import com.piwko.booking.api.form.get.GetUserForm;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    @Schema(description = "generated security token to put in Authorization header for future requests")
    private String token;

    @Schema(description = "logged in user data")
    private GetUserForm user;

}
