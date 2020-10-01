package com.piwko.booking.persistence.repository;

import com.piwko.booking.persistence.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
