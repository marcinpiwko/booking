package com.piwko.booking.api.swagger;

import com.piwko.booking.api.form.common.ErrorResponse;
import com.piwko.booking.api.form.common.IdResponse;
import com.piwko.booking.api.form.get.GetServiceForm;
import com.piwko.booking.api.form.get.GetServicesForm;
import com.piwko.booking.api.form.patch.PatchServiceForm;
import com.piwko.booking.api.form.post.PostServiceForm;
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
import java.nio.file.AccessDeniedException;
import java.util.Optional;

@Tag(name = "services", description = "The service API")
@RequestMapping("/services")
public interface ServiceApi {

    @Operation(summary = "Get service for specified id", description = "Get service for specified id", tags = "services")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(schema = @Schema(implementation = GetServiceForm.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<GetServiceForm> getService(@PathVariable("id") Long id) throws ResourceNotFoundException;

    @Operation(summary = "Get services", description = "Get services", tags = "services")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(schema = @Schema(implementation = GetServicesForm.class)))
    })
    @GetMapping(produces = "application/json")
    ResponseEntity<GetServicesForm> getServices(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size, @RequestParam Optional<String> sortBy, @RequestParam Optional<String> companyCode, @RequestParam Optional<String> locationCode, @RequestParam Optional<String> employee);

    @Operation(summary = "Create new service", description = "Create new service", tags = "services", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = IdResponse.class))),
            @ApiResponse(responseCode = "409", description = "Not unique", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PreAuthorize("hasAnyRole('ROLE_COMPANY_USER', 'ROLE_ADMIN')")
    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<IdResponse> createService(@Valid @RequestBody PostServiceForm postServiceForm) throws ResourceNotUniqueException, ResourceNotFoundException;

    @Operation(summary = "Modify service for specified id", description = "Modify service for specified id", tags = "services", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Not unique", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PreAuthorize("hasAnyRole('ROLE_COMPANY_USER', 'ROLE_ADMIN')")
    @PatchMapping(value = "/{id}", consumes = "application/json")
    ResponseEntity<Void> modifyService(@PathVariable("id") Long id, @Valid @RequestBody PatchServiceForm patchServiceForm) throws ResourceNotFoundException, ResourceNotUniqueException, AccessDeniedException;

    @Operation(summary = "Delete service for specified id", description = "Delete service for specified id", tags = "services", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PreAuthorize("hasAnyRole('ROLE_COMPANY_USER', 'ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> deleteService(@PathVariable("id") Long id) throws ResourceNotFoundException, AccessDeniedException;

    @Operation(summary = "Refresh service cache", description = "Refresh service cache", tags = "services", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "No content")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/refresh")
    ResponseEntity<Void> refreshServices();
}
