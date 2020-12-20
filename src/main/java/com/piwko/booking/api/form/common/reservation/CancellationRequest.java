package com.piwko.booking.api.form.common.reservation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancellationRequest {

    @Schema(description = "cancellation reason")
    private String reason;
}
