package com.piwko.booking.api.swagger;

import com.piwko.booking.api.form.common.ErrorResponse;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "imports", description = "The import API")
@RequestMapping("/imports")
public interface ImportApi {

    @Operation(summary = "Import services", description = "Import services", tags = "imports", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PreAuthorize("hasRole('ROLE_COMPANY_USER')")
    @PostMapping(value = "/services")
    ResponseEntity<Void> importServices(@RequestParam MultipartFile file) throws ResourceNotFoundException, ResourceNotUniqueException;

    @Operation(summary = "Import employees", description = "Import employees", tags = "imports", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PreAuthorize("hasRole('ROLE_COMPANY_USER')")
    @PostMapping(value = "/employees")
    ResponseEntity<Void> importEmployees(@RequestParam MultipartFile file) throws ResourceNotFoundException, ResourceNotUniqueException;

    @Operation(summary = "Import locations", description = "Import locations", tags = "imports", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PreAuthorize("hasRole('ROLE_COMPANY_USER')")
    @PostMapping(value = "/locations")
    ResponseEntity<Void> importLocations(@RequestParam MultipartFile file) throws ResourceNotFoundException, ResourceNotUniqueException;
}
