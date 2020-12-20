package com.piwko.booking.api.form.patch;

import com.piwko.booking.api.form.interfaces.PostPatchUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class PatchUserForm implements PostPatchUser {

    @Schema(description = "email as username", example = "test@test.com")
    @Email(message = "email does not match the pattern eg. test@test.com")
    private String email;

    @Schema(description = "user's password")
    private String password;

    @Schema(description = "user's phone number")
    @Pattern(regexp = "[0-9]{9}", message = "phoneNumber does not match the pattern eg. 123123123")
    private String phoneNumber;
}
