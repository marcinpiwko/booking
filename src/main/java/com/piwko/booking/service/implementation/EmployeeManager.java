package com.piwko.booking.service.implementation;

import com.piwko.booking.persistence.EntityFactory;
import com.piwko.booking.persistence.cache.Cache;
import com.piwko.booking.persistence.cache.GlobalCache;
import com.piwko.booking.persistence.model.Employee;
import com.piwko.booking.persistence.repository.EmployeeRepository;
import com.piwko.booking.persistence.search.EmployeeSearchCriteria;
import com.piwko.booking.service.interfaces.EmployeeService;
import com.piwko.booking.util.CollectionsUtil;
import com.piwko.booking.util.StringUtil;
import com.piwko.booking.util.WorkingHoursUtil;
import com.piwko.booking.util.exception.ApplicationException;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import com.piwko.booking.util.exception.ResourceNotUniqueException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeManager implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final Cache<Employee> cache = GlobalCache.getInstance().getEmployees();

    private static final String ENTITY_NAME = EntityFactory.getEntityName(Employee.class);

    @Override
    public Employee getEmployee(Long id) throws ResourceNotFoundException {
        Optional<Employee> cachedEmployee = cache.get(id);
        if (!cachedEmployee.isPresent()) {
            Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(ENTITY_NAME, "id", id));
            Hibernate.initialize(employee.getServices());
            cache.put(employee);
            return employee;
        }
        return cachedEmployee.get();
    }

    @Override
    public Employee getEmployee(String code) throws ResourceNotFoundException {
        Optional<Employee> cachedEmployee = cache.get(code);
        if (!cachedEmployee.isPresent()) {
            Employee employee = employeeRepository.findByCode(code)
                    .orElseThrow(() -> new ResourceNotFoundException(ENTITY_NAME, "code", code));
            Hibernate.initialize(employee.getServices());
            cache.put(employee);
            return employee;
        }
        return cachedEmployee.get();
    }

    @Override
    public Page<Employee> getEmployees(EmployeeSearchCriteria searchCriteria, Pageable pageable) {
        Page<Employee> employees = getEmployeesForSpecificSearchCriteria(searchCriteria, pageable);
        employees.forEach(e -> Hibernate.initialize(e.getServices()));
        return employees;
    }

    @Override
    public Long createEmployee(Employee employee) throws ResourceNotUniqueException {
        validateEmployee(employee);
        employeeRepository.save(employee);
        return cache.put(employee);
    }

    @Override
    public void updateEmployee(Long id, Employee employee) throws ResourceNotFoundException, ResourceNotUniqueException {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY_NAME, "id", id));
        if (employee.getLocation() == null) {
            employee.setLocation(existingEmployee.getLocation());
        }
        validateEmployee(employee);
        if (!StringUtil.isEmpty(employee.getFirstName())) {
            existingEmployee.setFirstName(employee.getFirstName());
        }
        if (!StringUtil.isEmpty(employee.getLastName())) {
            existingEmployee.setLastName(employee.getLastName());
        }
        if (!CollectionsUtil.isEmpty(employee.getServices())) {
            existingEmployee.setServices(employee.getServices());
        }
        if (employee.getWorkingHours() != null) {
            WorkingHoursUtil.updateWorkingHours(existingEmployee.getWorkingHours(), employee.getWorkingHours());
        }
        employeeRepository.save(existingEmployee);
        Hibernate.initialize(existingEmployee.getServices());
        cache.put(existingEmployee);
    }

    @Override
    public void createEmployees(List<Employee> employees) throws ResourceNotUniqueException {
        for (Employee e : employees) {
            validateEmployee(e);
        }
        employeeRepository.saveAll(employees);
        employees.forEach(cache::put);
    }

    @Override
    public void deleteEmployee(Long id) throws ResourceNotFoundException {
        if (!cache.existsById(id)) {
            throw new ResourceNotFoundException(ENTITY_NAME, "id", id);
        }
        employeeRepository.softDeleteById(id);
        cache.remove(id);
    }

    @Override
    public boolean isEmployeeInCompany(Long employeeId, Long companyId) {
        Optional<Employee> emp = cache.get(employeeId);
        return (emp.isPresent() && emp.get().getLocation().getCompany().getId().equals(companyId));
    }

    private Page<Employee> getEmployeesForSpecificSearchCriteria(EmployeeSearchCriteria searchCriteria, Pageable pageable) {
        if (!StringUtil.isEmpty(searchCriteria.getLocationCode())) {
            if (!StringUtil.isEmpty(searchCriteria.getServiceCode())) {
                return employeeRepository.findAllByLocationAndService(searchCriteria.getLocationCode(), searchCriteria.getServiceCode(), pageable);
            }
            return employeeRepository.findAllByLocation(searchCriteria.getLocationCode(), pageable);
        } else if (!StringUtil.isEmpty(searchCriteria.getCompanyCode())) {
            return employeeRepository.findAllByCompany(searchCriteria.getCompanyCode(), pageable);
        }
        return employeeRepository.findAll(pageable);
    }

    private void validateEmployee(Employee employee) throws ResourceNotUniqueException {
        if (employeeRepository.existsByFirstNameAndLastNameAndLocation(employee.getFirstName(), employee.getLastName(), employee.getLocation())) {
            throw new ResourceNotUniqueException(ENTITY_NAME, "firstName and lastName for Location", employee.getLocation().getCode());
        }
        if (cache.existsByCode(employee.getCode())) {
            throw new ResourceNotUniqueException(ENTITY_NAME, "code", employee.getCode());
        }
        validateEmployeeWorkingHours(employee);
    }

    private void validateEmployeeWorkingHours(Employee employee) {
        if (employee.getWorkingHours() != null) {
            employee.getWorkingHours().getWorkingHoursByDayOfWeek()
                    .forEach((k, v) -> {
                        if (!WorkingHoursUtil.isWithinWorkingHours(v, employee.getLocation().getWorkingHours().getWorkingHoursByDayOfWeek().get(k))) {
                            throw new ApplicationException("Employee working hours are not within location");
                        }
                    });
        }
    }

    @Override
    @EventListener(ContextRefreshedEvent.class)
    public void refreshCache() {
        cache.clear();
        List<Employee> employees = employeeRepository.findAll();
        for (Employee e : employees) {
            Hibernate.initialize(e.getServices());
            cache.put(e);
        }
    }
}
