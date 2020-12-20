package com.piwko.booking.api.form.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class IdResponse {

    @Schema(description = "object Id")
    private Long id;
}
