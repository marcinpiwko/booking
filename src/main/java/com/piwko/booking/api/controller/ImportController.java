package com.piwko.booking.api.controller;

import com.piwko.booking.api.swagger.ImportApi;
import com.piwko.booking.persistence.model.User;
import com.piwko.booking.service.imports.processor.EmployeeImportProcessor;
import com.piwko.booking.service.imports.processor.LocationImportProcessor;
import com.piwko.booking.service.imports.processor.ServiceImportProcessor;
import com.piwko.booking.service.interfaces.UserService;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import com.piwko.booking.util.exception.ResourceNotUniqueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@Controller
@RequiredArgsConstructor
public class ImportController implements ImportApi {

    private final ServiceImportProcessor serviceImportProcessor;

    private final EmployeeImportProcessor employeeImportProcessor;

    private final LocationImportProcessor locationImportProcessor;

    private final UserService userService;

    @Override
    public ResponseEntity<Void> importServices(MultipartFile file) throws ResourceNotFoundException, ResourceNotUniqueException {
        log.info("POST /api/imports/services");
        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        serviceImportProcessor.process(file, user.getCompany());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> importEmployees(MultipartFile file) throws ResourceNotFoundException, ResourceNotUniqueException {
        log.info("POST /api/imports/employees");
        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        employeeImportProcessor.process(file, user.getCompany());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> importLocations(MultipartFile file) throws ResourceNotFoundException, ResourceNotUniqueException {
        log.info("POST /api/imports/locations");
        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        locationImportProcessor.process(file, user.getCompany());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
