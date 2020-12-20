package com.piwko.booking.persistence.repository;

import com.piwko.booking.persistence.model.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceRepository extends SoftDeleteRepository<Service, Long>, CodeNameRepository<Service> {

    @Query(value = "SELECT * FROM SERVICES WHERE SRV_CMP_ID IN (SELECT CMP_ID FROM COMPANIES WHERE LOWER(CMP_CODE) = LOWER(?1) AND NOT CMP_REMOVED) AND NOT SRV_REMOVED", nativeQuery = true)
    Page<Service> findAllByCompanyCode(String companyCode, Pageable pageable);

    @Query(value = "SELECT * FROM SERVICES WHERE SRV_ID IN " +
            "(SELECT SERVICES_SRV_ID FROM EMPLOYEES_SERVICES WHERE EMPLOYEES_EMP_ID IN (SELECT EMP_ID FROM EMPLOYEES WHERE EMP_LOC_ID = " +
            "(SELECT LOC_ID FROM LOCATIONS WHERE LOWER(LOC_CODE) = LOWER(?1) AND NOT LOC_REMOVED) AND NOT EMP_REMOVED)) AND NOT SRV_REMOVED", nativeQuery = true)
    Page<Service> findAllByLocationCode(String locationCode, Pageable pageable);

    @Query(value = "SELECT * FROM SERVICES WHERE SRV_ID IN (SELECT SERVICES_SRV_ID FROM EMPLOYEES_SERVICES WHERE EMPLOYEES_EMP_ID = " +
            "(SELECT EMP_ID FROM EMPLOYEES WHERE LOWER(EMP_FIRST_NAME) = LOWER(?1) AND LOWER(EMP_LAST_NAME) = LOWER(?2) AND NOT EMP_REMOVED)) AND NOT SRV_REMOVED", nativeQuery = true)
    Page<Service> findAllByEmployee(String firstName, String lastName, Pageable pageable);

    @Query(value = "SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM SERVICES s WHERE SRV_CMP_ID = ?1 AND NOT SRV_REMOVED", nativeQuery = true)
    boolean existsByCompanyId(Long companyId);

    @Query(value = "SELECT * FROM SERVICES WHERE LOWER(SRV_CODE) = LOWER(?1) AND SRV_CMP_ID = " +
            "(SELECT CMP_ID FROM COMPANIES WHERE LOWER(CMP_CODE) = LOWER(?2) AND NOT CMP_REMOVED) AND NOT SRV_REMOVED", nativeQuery = true)
    Optional<Service> findByCodeAndCompanyCode(String serviceCode, String companyCode);

}
