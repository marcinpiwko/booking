package com.piwko.booking.api.swagger;

import com.piwko.booking.api.form.common.authentication.AuthenticationRequest;
import com.piwko.booking.api.form.common.authentication.AuthenticationResponse;
import com.piwko.booking.api.form.common.ErrorResponse;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import com.piwko.booking.util.exception.UnauthorizedException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Tag(name = "authentication", description = "The authentication API")
public interface AuthenticationApi {

    @Operation(summary = "Login", description = "User login", tags = "authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest authenticationRequest, @RequestHeader(HttpHeaders.AUTHORIZATION) Optional<String> token) throws UnauthorizedException, ResourceNotFoundException;

    @Operation(summary = "Logout", description = "User logout", tags = "authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping(value = "/logout")
    ResponseEntity<Void> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String token);

}
