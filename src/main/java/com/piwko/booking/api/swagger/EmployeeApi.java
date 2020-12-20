package com.piwko.booking.api.swagger;

import com.piwko.booking.api.form.common.ErrorResponse;
import com.piwko.booking.api.form.common.IdResponse;
import com.piwko.booking.api.form.get.GetEmployeeForm;
import com.piwko.booking.api.form.get.GetEmployeesForm;
import com.piwko.booking.api.form.patch.PatchEmployeeForm;
import com.piwko.booking.api.form.post.PostEmployeeForm;
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

@Tag(name = "employees", description = "The employee API")
@RequestMapping("/employees")
public interface EmployeeApi {

    @Operation(summary = "Get employee for specified id", description = "Get employee for specified id", tags = "employees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(schema = @Schema(implementation = GetEmployeeForm.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<GetEmployeeForm> getEmployee(@PathVariable("id") Long id) throws ResourceNotFoundException;

    @Operation(summary = "Get employees", description = "Get employees", tags = "employees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(schema = @Schema(implementation = GetEmployeesForm.class)))
    })
    @GetMapping(produces = "application/json")
    ResponseEntity<GetEmployeesForm> getEmployees(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size, @RequestParam Optional<String> sortBy, @RequestParam Optional<String> locationCode, @RequestParam Optional<String> companyCode, @RequestParam Optional<String> serviceCode);

    @Operation(summary = "Create new employee", description = "Create new employee", tags = "employees", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = IdResponse.class))),
            @ApiResponse(responseCode = "409", description = "Not unique", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PreAuthorize("hasAnyRole('ROLE_COMPANY_USER', 'ROLE_ADMIN')")
    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<IdResponse> createEmployee(@Valid @RequestBody PostEmployeeForm postEmployeeForm) throws ResourceNotUniqueException, ResourceNotFoundException;

    @Operation(summary = "Modify employee for specified id", description = "Modify employee for specified id", tags = "employees", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Not unique", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PreAuthorize("hasAnyRole('ROLE_COMPANY_USER', 'ROLE_ADMIN')")
    @PatchMapping(value = "/{id}", consumes = "application/json")
    ResponseEntity<Void> modifyEmployee(@PathVariable("id") Long id, @Valid @RequestBody PatchEmployeeForm patchEmployeeForm) throws ResourceNotFoundException, ResourceNotUniqueException;

    @Operation(summary = "Delete employee for specified id", description = "Delete employee for specified id", tags = "employees", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PreAuthorize("hasAnyRole('ROLE_COMPANY_USER', 'ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long id) throws ResourceNotFoundException;

    @Operation(summary = "Refresh employee cache", description = "Refresh employee cache", tags = "employees", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "No content")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/refresh")
    ResponseEntity<Void> refreshEmployees();
}
