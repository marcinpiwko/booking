package com.piwko.booking.api.swagger;

import com.piwko.booking.api.form.common.ErrorResponse;
import com.piwko.booking.api.form.common.IdResponse;
import com.piwko.booking.api.form.get.GetLocationForm;
import com.piwko.booking.api.form.get.GetLocationsForm;
import com.piwko.booking.api.form.patch.PatchLocationForm;
import com.piwko.booking.api.form.post.PostLocationForm;
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
import java.util.Optional;

@Tag(name = "locations", description = "The location API")
@RequestMapping("/locations")
public interface LocationApi {

    @Operation(summary = "Get location for specified id", description = "Get location for specified id", tags = "locations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(schema = @Schema(implementation = GetLocationForm.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<GetLocationForm> getLocation(@PathVariable("id") Long id) throws ResourceNotFoundException;

    @Operation(summary = "Get locations", description = "Get locations", tags = "locations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(schema = @Schema(implementation = GetLocationsForm.class)))
    })
    @GetMapping(produces = "application/json")
    ResponseEntity<GetLocationsForm> getLocations(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size, @RequestParam Optional<String> sortBy, @RequestParam Optional<String> companyCode);

    @Operation(summary = "Create new location", description = "Create new location", tags = "locations", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = IdResponse.class))),
            @ApiResponse(responseCode = "409", description = "Not unique", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PreAuthorize("hasAnyRole('ROLE_COMPANY_USER', 'ROLE_ADMIN')")
    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<IdResponse> createLocation(@Valid @RequestBody PostLocationForm postLocationForm) throws ResourceNotUniqueException, ResourceNotFoundException;

    @Operation(summary = "Modify location for specified id", description = "Modify location for specified id", tags = "locations", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Not unique", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PreAuthorize("hasAnyRole('ROLE_COMPANY_USER', 'ROLE_ADMIN')")
    @PatchMapping(value = "/{id}", consumes = "application/json")
    ResponseEntity<Void> modifyLocation(@PathVariable("id") Long id, @Valid @RequestBody PatchLocationForm patchLocationForm) throws ResourceNotFoundException, ResourceNotUniqueException;

    @Operation(summary = "Delete location for specified id", description = "Delete location for specified id", tags = "locations", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PreAuthorize("hasAnyRole('ROLE_COMPANY_USER', 'ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> deleteLocation(@PathVariable("id") Long id) throws ResourceNotFoundException;

    @Operation(summary = "Refresh location cache", description = "Refresh location cache", tags = "locations", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "No content")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/refresh")
    ResponseEntity<Void> refreshLocations();
}
