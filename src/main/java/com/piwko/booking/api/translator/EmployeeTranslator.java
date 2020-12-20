package com.piwko.booking.api.translator;

import com.piwko.booking.api.form.get.GetEmployeeForm;
import com.piwko.booking.api.form.interfaces.PostPatchEmployee;
import com.piwko.booking.persistence.EntityFactory;
import com.piwko.booking.persistence.model.Employee;
import com.piwko.booking.persistence.model.Service;
import com.piwko.booking.service.interfaces.LocationService;
import com.piwko.booking.service.interfaces.ServiceService;
import com.piwko.booking.util.CollectionsUtil;
import com.piwko.booking.util.StringUtil;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EmployeeTranslator {

    private final LocationService locationService;

    private final ServiceService serviceService;

    private final WorkingHoursTranslator workingHoursTranslator;

    public GetEmployeeForm translate(Employee employee) {
        GetEmployeeForm employeeForm = new GetEmployeeForm();
        employeeForm.setId(employee.getId());
        employeeForm.setFirstName(employee.getFirstName());
        employeeForm.setLastName(employee.getLastName());
        employeeForm.setEmployeeCode(employee.getCode());
        employeeForm.setLocationCode(employee.getLocation().getCode());
        if (employee.getServices() != null) {
            employeeForm.setServiceCodes(employee.getServices().stream().map(Service::getCode).collect(Collectors.toList()));
        }
        employeeForm.setWorkingHours(workingHoursTranslator.translate(employee.getWorkingHours()));
        return employeeForm;
    }

    public Employee translate(PostPatchEmployee employeeForm) throws ResourceNotFoundException {
        Employee employee = EntityFactory.newEntityInstance(Employee.class);
        employee.setFirstName(employeeForm.getFirstName());
        employee.setLastName(employeeForm.getLastName());
        employee.setCode(employeeForm.getCode());
        if (!StringUtil.isEmpty(employeeForm.getLocationCode())) {
            employee.setLocation(locationService.getLocation(employeeForm.getLocationCode()));
        }
        if (employeeForm.getWorkingHours() != null) {
            employee.setWorkingHours(workingHoursTranslator.translate(employeeForm.getWorkingHours()));
        }
        if (!CollectionsUtil.isEmpty(employeeForm.getServiceCodes())) {
            Set<Service> services = new HashSet<>();
            for (String serviceCode : employeeForm.getServiceCodes()) {
                Service service = serviceService.getService(serviceCode);
                if (!service.getCompany().getCode().equals(employee.getLocation().getCompany().getCode())) {
                    throw new AccessDeniedException("You don't have permission to service " + serviceCode);
                }
                services.add(service);
            }
            employee.setServices(services);
        } else {
            employee.setServices(Collections.emptySet());
        }
        return employee;
    }
}
