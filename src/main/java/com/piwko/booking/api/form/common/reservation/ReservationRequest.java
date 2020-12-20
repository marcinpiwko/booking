package com.piwko.booking.api.form.common.reservation;

import com.piwko.booking.api.form.post.PostVirtualUserForm;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationRequest {

    @Schema(description = "reservation date")
    @NotNull(message = "reservation date can not be null")
    private LocalDateTime date;

    @Size(min = 3, max = 10, message = "min code size = 3, max = 10")
    @Schema(description = "employee business code")
    @NotBlank(message = "employee code can not be blank")
    private String employeeCode;

    @Size(min = 3, max = 10, message = "min code size = 3, max = 10")
    @Schema(description = "service business code")
    @NotBlank(message = "service code can not be blank")
    private String serviceCode;

    @Size(min = 3, max = 10, message = "min code size = 3, max = 10")
    @Schema(description = "location business code")
    @NotBlank(message = "location code can not be blank")
    private String locationCode;

    private PostVirtualUserForm user;
}
