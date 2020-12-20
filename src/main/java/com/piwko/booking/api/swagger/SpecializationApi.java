package com.piwko.booking.api.swagger;

import com.piwko.booking.api.form.common.ErrorResponse;
import com.piwko.booking.api.form.common.IdResponse;
import com.piwko.booking.api.form.get.GetSpecializationForm;
import com.piwko.booking.api.form.get.GetSpecializationsForm;
import com.piwko.booking.api.form.patch.PatchSpecializationForm;
import com.piwko.booking.api.form.post.PostSpecializationForm;
import com.piwko.booking.util.exception.ResourceInUseException;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import com.piwko.booking.util.exception.ResourceNotUniqueException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "specializations", description = "The specialization API")
@RequestMapping("/specializations")
public interface SpecializationApi {

    @Operation(summary = "Get specialization for specified id", description = "Get specialization for specified id", tags = "specializations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(schema = @Schema(implementation = GetSpecializationForm.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<GetSpecializationForm> getSpecialization(@PathVariable("id") Long id) throws ResourceNotFoundException;

    @Operation(summary = "Get specializations", description = "Get specializations", tags = "specializations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(schema = @Schema(implementation = GetSpecializationsForm.class)))
    })
    @GetMapping(produces = "application/json")
    ResponseEntity<GetSpecializationsForm> getSpecializations();

    @Operation(summary = "Create new specialization", description = "Create new specialization", tags = "specializations", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = IdResponse.class))),
            @ApiResponse(responseCode = "409", description = "Not unique", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<IdResponse> createSpecialization(@Valid @RequestBody PostSpecializationForm postSpecializationForm) throws ResourceNotUniqueException;

    @Operation(summary = "Modify specialization for specified id", description = "Modify specialization for specified id", tags = "specializations", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Not unique", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping(value = "/{id}", consumes = "application/json")
    ResponseEntity<Void> modifySpecialization(@PathVariable("id") Long id, @Valid @RequestBody PatchSpecializationForm patchSpecializationForm) throws ResourceNotFoundException, ResourceNotUniqueException;

    @Operation(summary = "Delete specialization for specified id", description = "Delete specialization for specified id", tags = "specializations", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> deleteSpecialization(@PathVariable("id") Long id) throws ResourceNotFoundException, ResourceInUseException;

    @Operation(summary = "Refresh specialization cache", description = "Refresh specialization cache", tags = "specializations", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "No content")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/refresh")
    ResponseEntity<Void> refreshSpecializations();
}