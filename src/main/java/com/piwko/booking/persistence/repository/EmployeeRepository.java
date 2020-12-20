package com.piwko.booking.persistence.repository;

import com.piwko.booking.persistence.model.Employee;
import com.piwko.booking.persistence.model.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends SoftDeleteRepository<Employee, Long> {

    @Query(value = "SELECT * FROM EMPLOYEES WHERE EMP_LOC_ID = (SELECT LOC_ID FROM LOCATIONS WHERE LOWER(LOC_CODE) = LOWER(?1)) AND NOT EMP_REMOVED", nativeQuery = true)
    Page<Employee> findAllByLocation(String locationCode, Pageable pageable);

    @Query(value = "SELECT * FROM EMPLOYEES WHERE EMP_LOC_ID = (SELECT LOC_ID FROM LOCATIONS WHERE LOWER(LOC_CODE) = LOWER(?1) AND NOT LOC_REMOVED)" +
            " AND EMP_ID IN (SELECT EMPLOYEES_EMP_ID FROM EMPLOYEES_SERVICES WHERE SERVICES_SRV_ID = (SELECT SRV_ID FROM SERVICES WHERE LOWER(SRV_CODE) = LOWER(?2))) AND NOT EMP_REMOVED", nativeQuery = true)
    Page<Employee> findAllByLocationAndService(String locationCode, String serviceCode, Pageable pageable);

    @Query(value = "SELECT * FROM EMPLOYEES WHERE EMP_LOC_ID IN (SELECT LOC_ID FROM LOCATIONS WHERE LOC_CMP_ID = (SELECT CMP_ID FROM COMPANIES WHERE LOWER(CMP_CODE) = LOWER(?1)) AND NOT LOC_REMOVED) AND NOT EMP_REMOVED", nativeQuery = true)
    Page<Employee> findAllByCompany(String companyCode, Pageable pageable);

    boolean existsByFirstNameAndLastNameAndLocation(String firstName, String lastName, Location location);

    @Query(value = "SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END FROM EMPLOYEES e WHERE EMP_ID = ?1 AND EMP_LOC_ID IN " +
            "(SELECT LOC_ID FROM LOCATIONS WHERE LOC_CMP_ID = ?2)", nativeQuery = true)
    boolean existsByCompany(Long employeeId, Long companyId);

    @Query(value = "SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END FROM EMPLOYEES e WHERE EMP_ID = ?1 AND EMP_LOC_ID = ?2", nativeQuery = true)
    boolean existsByLocation(Long employeeId, Long locationId);

    Optional<Employee> findByCode(String code);

    boolean existsByCode(String code);

}
