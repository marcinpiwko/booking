package com.piwko.booking.api.form.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

    @Schema(description = "error date")
    private LocalDateTime timestamp;

    @Schema(description = "http error status")
    private int status;

    @Schema(description = "http error code")
    private String error;

    @Schema(description = "error message")
    private String message;

    @Schema(description = "requested endpoint")
    private String path;
}
