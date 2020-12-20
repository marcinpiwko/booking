package com.piwko.booking.api.controller;

import com.piwko.booking.api.PageProperties;
import com.piwko.booking.api.form.common.IdResponse;
import com.piwko.booking.api.form.get.GetEmployeeForm;
import com.piwko.booking.api.form.get.GetEmployeesForm;
import com.piwko.booking.api.form.patch.PatchEmployeeForm;
import com.piwko.booking.api.form.post.PostEmployeeForm;
import com.piwko.booking.api.swagger.EmployeeApi;
import com.piwko.booking.api.translator.EmployeeTranslator;
import com.piwko.booking.persistence.model.User;
import com.piwko.booking.persistence.search.EmployeeSearchCriteria;
import com.piwko.booking.service.interfaces.EmployeeService;
import com.piwko.booking.service.interfaces.LocationService;
import com.piwko.booking.service.interfaces.UserService;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import com.piwko.booking.util.exception.ResourceNotUniqueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@Log4j2
@RestController
@RequiredArgsConstructor
public class EmployeeController implements EmployeeApi {

    private final EmployeeService employeeService;

    private final EmployeeTranslator employeeTranslator;

    private final UserService userService;

    private final LocationService locationService;

    private final PageProperties pageProperties;

    @Override
    public ResponseEntity<GetEmployeeForm> getEmployee(Long id) throws ResourceNotFoundException {
        log.info("GET /api/employees/" + id);
        return new ResponseEntity<>(employeeTranslator.translate(employeeService.getEmployee(id)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetEmployeesForm> getEmployees(Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy, Optional<String> locationCode, Optional<String> companyCode, Optional<String> serviceCode) {
        log.info("GET /api/employees");
        return new ResponseEntity<>(new GetEmployeesForm(
                employeeService.getEmployees(new EmployeeSearchCriteria(companyCode.orElse(null), locationCode.orElse(null), serviceCode.orElse(null)),
                        PageRequest.of(page.orElse(pageProperties.getNumber()), size.orElse(pageProperties.getSize()), sortBy.map(Sort::by).orElseGet(Sort::unsorted)))
                        .map(employeeTranslator::translate)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<IdResponse> createEmployee(@Valid PostEmployeeForm postEmployeeForm) throws ResourceNotUniqueException, ResourceNotFoundException {
        log.info("POST /api/employees");
        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!locationService.existsByCompany(postEmployeeForm.getLocationCode(), user.getCompany().getCode())) {
            throw new AccessDeniedException("You don't have permission to create employee for location " + postEmployeeForm.getLocationCode());
        }
        IdResponse idResponse = new IdResponse(employeeService.createEmployee(employeeTranslator.translate(postEmployeeForm)));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.LOCATION, "/api/employees/" + idResponse.getId());
        return new ResponseEntity<>(idResponse, httpHeaders, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> modifyEmployee(Long id, @Valid PatchEmployeeForm patchEmployeeForm) throws ResourceNotFoundException, ResourceNotUniqueException {
        log.info("PATCH /api/employees/" + id);
        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!employeeService.isEmployeeInCompany(id, user.getCompany().getId())) {
            throw new AccessDeniedException("You don't have permission to modify employee with id " + id);
        }
        employeeService.updateEmployee(id, employeeTranslator.translate(patchEmployeeForm));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> deleteEmployee(Long id) throws ResourceNotFoundException {
        log.info("DELETE /api/employees/" + id);
        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!employeeService.isEmployeeInCompany(id, user.getCompany().getId())) {
            throw new AccessDeniedException("You don't have permission to delete employee with id " + id);
        }
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> refreshEmployees() {
        log.info("POST /api/employees/refresh");
        employeeService.refreshCache();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
