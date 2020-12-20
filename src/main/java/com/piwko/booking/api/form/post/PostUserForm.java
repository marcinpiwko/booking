package com.piwko.booking.api.form.post;

import com.piwko.booking.api.form.interfaces.PostPatchUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
public class PostUserForm implements PostPatchUser {

    @Schema(description = "email as username", example = "test@test.com")
    @Email(message = "email does not match the pattern eg. test@test.com")
    @NotBlank(message = "email can not be empty")
    private String email;

    @Schema(description = "user's password")
    @NotBlank(message = "password can not be empty")
    private String password;

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

    @Schema(description = "user's associated company (only for COMPANY_USER, account will require approval)")
    private String companyCode;

    @Schema(description = "user's company, first account registration (only for COMPANY_USER, account will require approval)")
    private PostCompanyForm company;
}
