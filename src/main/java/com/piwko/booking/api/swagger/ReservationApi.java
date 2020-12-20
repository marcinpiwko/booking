package com.piwko.booking.api.swagger;

import com.piwko.booking.api.form.common.ErrorResponse;
import com.piwko.booking.api.form.common.IdResponse;
import com.piwko.booking.api.form.common.reservation.CancellationRequest;
import com.piwko.booking.api.form.common.reservation.ReservationRequest;
import com.piwko.booking.api.form.get.GetReservationForm;
import com.piwko.booking.api.form.get.GetReservationsForm;
import com.piwko.booking.util.exception.ReservationException;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Tag(name = "reservations", description = "The reservation API")
@RequestMapping("/reservations")
public interface ReservationApi {

    @Operation(summary = "Get reservation for specified id", description = "Get reservation for specified id", tags = "reservations", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(schema = @Schema(implementation = GetReservationForm.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<GetReservationForm> getReservation(@PathVariable("id") Long id) throws ResourceNotFoundException;

    @Operation(summary = "Get reservations", description = "Get reservations", tags = "reservations", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(schema = @Schema(implementation = GetReservationsForm.class)))
    })
    @GetMapping(produces = "application/json")
    ResponseEntity<GetReservationsForm> getReservations(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size, @RequestParam Optional<String> sortBy, @RequestParam Optional<String> locationCode, @RequestParam Optional<String> status) throws ResourceNotFoundException;

    @Operation(summary = "Create new reservation", description = "Create new reservation", tags = "reservations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = IdResponse.class))),
            @ApiResponse(responseCode = "409", description = "Not unique", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<IdResponse> createReservation(@Valid @RequestBody ReservationRequest reservationRequest) throws ReservationException, ResourceNotFoundException;

    @Operation(summary = "Cancel reservation for specified id", description = "Cancel reservation for specified id", tags = "reservations", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping(value = "/{id}/cancel", consumes = "application/json")
    ResponseEntity<Void> cancelReservation(@PathVariable("id") Long id, @Valid @RequestBody CancellationRequest cancellationRequest) throws ResourceNotFoundException, ReservationException;
}
