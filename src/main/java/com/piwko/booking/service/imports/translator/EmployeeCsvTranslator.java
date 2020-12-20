package com.piwko.booking.service.imports.translator;

import com.piwko.booking.persistence.EntityFactory;
import com.piwko.booking.persistence.model.Company;
import com.piwko.booking.persistence.model.Employee;
import com.piwko.booking.persistence.model.Location;
import com.piwko.booking.persistence.model.Service;
import com.piwko.booking.service.imports.dto.EmployeeCsv;
import com.piwko.booking.service.interfaces.LocationService;
import com.piwko.booking.service.interfaces.ServiceService;
import com.piwko.booking.util.CollectionsUtil;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.*;

@Validated
@Component
@RequiredArgsConstructor
public class EmployeeCsvTranslator {

    private final LocationService locationService;

    private final ServiceService serviceService;

    private final WorkingHoursCsvTranslator workingHoursCsvTranslator;

    public Employee translate(@Valid EmployeeCsv employeeCsv, Company company) throws ResourceNotFoundException {
        Employee employee = EntityFactory.newEntityInstance(Employee.class);
        employee.setFirstName(employeeCsv.getFirstName());
        employee.setLastName(employeeCsv.getLastName());
        employee.setCode(employeeCsv.getCode());
        employee.setLocation(getLocationByCodeAndCompanyCode(employeeCsv.getLocationCode(), company.getCode()));
        employee.setServices(getServicesByCodesAndCompanyCode(employeeCsv.getServiceCodes(), company.getCode()));
        employee.setWorkingHours(workingHoursCsvTranslator.translate(employeeCsv.getWorkingHours()));
        return employee;
    }

    private Location getLocationByCodeAndCompanyCode(String locationCode, String companyCode) throws ResourceNotFoundException {
        return locationService.getLocationByCodeAndCompanyCode(locationCode, companyCode);
    }

    private Set<Service> getServicesByCodesAndCompanyCode(List<String> codes, String companyCode) throws ResourceNotFoundException {
        if (CollectionsUtil.isEmpty(codes)) {
            return Collections.emptySet();
        }
        Set<Service> services = new HashSet<>();
        for (String code : codes) {
            services.add(serviceService.getServiceByCodeAndCompanyCode(code, companyCode));
        }
        return services;
    }
}
