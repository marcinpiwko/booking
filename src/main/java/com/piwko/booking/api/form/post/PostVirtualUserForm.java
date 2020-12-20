package com.piwko.booking.api.form.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
public class PostVirtualUserForm {

    @Schema(description = "email as username", example = "test@test.com")
    @Email(message = "email does not match the pattern eg. test@test.com")
    @NotBlank(message = "email can not be empty")
    private String email;

    @Schema(description = "user's first name")
    @NotBlank(message = "firstName can not be empty")
    private String firstName;

    @Schema(description = "user's last name")
    @NotBlank(message = "lastName can not be empty")
    private String lastName;

    @Schema(description = "user's birth date")
    private LocalDate birthDate;

    @Schema(description = "user's phone number")
    @Pattern(regexp = "[0-9]{9}", message = "phoneNumber does not match the pattern eg. 123123123")
    private String phoneNumber;

    @Schema(description = "user's sex (M or F)")
    @NotBlank
    @Pattern(regexp = "[MF]")
    private String gender;

}
