package com.piwko.booking.api.swagger;

import com.piwko.booking.api.form.common.ErrorResponse;
import com.piwko.booking.api.form.common.IdResponse;
import com.piwko.booking.api.form.get.GetCompaniesForm;
import com.piwko.booking.api.form.get.GetCompanyForm;
import com.piwko.booking.api.form.patch.PatchCompanyForm;
import com.piwko.booking.api.form.post.PostCompanyForm;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import com.piwko.booking.util.exception.ResourceNotUniqueException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Tag(name = "companies", description = "The company API")
@RequestMapping("/companies")
public interface CompanyApi {

    @Operation(summary = "Get company for specified id", description = "Get company for specified id", tags = "companies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(schema = @Schema(implementation = GetCompanyForm.class))),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<GetCompanyForm> getCompany(@PathVariable("id") Long id) throws ResourceNotFoundException;

    @Operation(summary = "Get companies", description = "Get companies", tags = "companies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful", content = @Content(schema = @Schema(implementation = GetCompaniesForm.class)))
    })
    @GetMapping(produces = "application/json")
    ResponseEntity<GetCompaniesForm> getCompanies(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size, @RequestParam Optional<String> sortBy);

    @Operation(summary = "Create new company", description = "Create new company", tags = "companies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = IdResponse.class))),
            @ApiResponse(responseCode = "409", description = "Not unique", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<IdResponse> createCompany(@Valid @RequestBody PostCompanyForm postCompanyForm) throws ResourceNotUniqueException;

    @Operation(summary = "Modify company for specified id", description = "Modify company for specified id", tags = "companies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Not unique", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping(value = "/{id}", consumes = "application/json")
    ResponseEntity<Void> modifyCompany(@PathVariable("id") Long id, @Valid @RequestBody PatchCompanyForm patchCompanyForm) throws ResourceNotFoundException, ResourceNotUniqueException;

    @Operation(summary = "Delete company for specified id", description = "Delete company for specified id", tags = "companies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content")
    })
    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> deleteCompany(@PathVariable("id") Long id);
}
