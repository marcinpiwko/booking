package com.piwko.booking.service.interfaces;

import com.piwko.booking.persistence.model.Employee;
import com.piwko.booking.persistence.search.EmployeeSearchCriteria;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import com.piwko.booking.util.exception.ResourceNotUniqueException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService extends CacheService {

    Employee getEmployee(Long id) throws ResourceNotFoundException;

    Employee getEmployee(String code) throws ResourceNotFoundException;

    Page<Employee> getEmployees(EmployeeSearchCriteria searchCriteria, Pageable pageable);

    Long createEmployee(Employee employee) throws ResourceNotUniqueException;

    void createEmployees(List<Employee> employees) throws ResourceNotUniqueException;

    void updateEmployee(Long id, Employee employee) throws ResourceNotFoundException, ResourceNotUniqueException;

    void deleteEmployee(Long id) throws ResourceNotFoundException;

    boolean isEmployeeInCompany(Long employeeId, Long companyId);
}
