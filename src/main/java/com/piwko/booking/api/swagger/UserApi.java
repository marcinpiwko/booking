package com.piwko.booking.api.swagger;

import com.piwko.booking.api.form.common.ErrorResponse;
import com.piwko.booking.api.form.common.IdResponse;
import com.piwko.booking.api.form.patch.PatchUserForm;
import com.piwko.booking.api.form.post.PostUserForm;
import com.piwko.booking.api.form.get.GetUserForm;
import com.piwko.booking.api.form.get.GetUsersForm;
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

@Tag(name = "users", description = "The users API")
@RequestMapping("/users")
public interface UserApi {

    @Operation(summary = "Get users", description = "Get users", tags = "users", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(schema = @Schema(implementation = GetUsersForm.class)))
    })
    @GetMapping(produces = "application/json")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    ResponseEntity<GetUsersForm> getUsers(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size, @RequestParam Optional<String> sortBy, @RequestParam Optional<Boolean> enabled, @RequestParam Optional<String> companyCode);

    @Operation(summary = "Get user by specified id", description = "Get user by specified id", tags = "users", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(schema = @Schema(implementation = GetUserForm.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(value = "/{id}", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<GetUserForm> getUser(@PathVariable("id") Long id) throws ResourceNotFoundException;

    @Operation(summary = "Get currently logged in user", description = "Get currently logged in user", tags = "users", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(schema = @Schema(implementation = GetUserForm.class)))
    })
    @GetMapping(value = "/me", produces = "application/json")
    ResponseEntity<GetUserForm> getMe() throws ResourceNotFoundException;

    @Operation(summary = "Create new user", description = "Create new user", tags = "users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = IdResponse.class))),
            @ApiResponse(responseCode = "409", description = "Not unique", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<String> createUser(@Valid @RequestBody PostUserForm postUserForm) throws ResourceNotUniqueException, ResourceNotFoundException;

    @Operation(summary = "Update user by specified id", description = "Update user by specified id", tags = "users", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "409", description = "Not unique", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping(value = "/me", consumes = "application/json", produces = "application/json")
    ResponseEntity<Void> updateMe(@Valid @RequestBody PatchUserForm patchUserForm, @RequestHeader("Authorization") String token) throws ResourceNotUniqueException, ResourceNotFoundException;

    @Operation(summary = "Enable user account", description = "Enable user account", tags = "users", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "No content") })
    @PatchMapping(value = "/{id}/enable")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    ResponseEntity<GetUsersForm> enableUser(@PathVariable("id") Long id) throws ResourceNotFoundException;
}
