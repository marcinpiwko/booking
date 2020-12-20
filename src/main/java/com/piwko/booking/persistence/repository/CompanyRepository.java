package com.piwko.booking.persistence.repository;

import com.piwko.booking.persistence.model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends SoftDeleteRepository<Company, Long>, CodeNameRepository<Company> {

    @Query(value = "SELECT * FROM COMPANIES WHERE CMP_ID IN (SELECT COMPANIES_CMP_ID FROM COMPANIES_SPECIALIZATIONS WHERE SPECIALIZATIONS_SPC_ID = (SELECT SPC_ID FROM SPECIALIZATIONS WHERE LOWER(SPC_CODE) = LOWER(?1))) " +
            "AND CMP_ID IN (SELECT LOC_CMP_ID FROM LOCATIONS WHERE LOC_ADR_ID IN (SELECT ADR_ID FROM ADDRESSES WHERE LOWER(ADR_CITY) = LOWER(?2)) AND NOT LOC_REMOVED) AND NOT CMP_REMOVED", nativeQuery = true)
    Page<Company> findAllBySpecializationAndCity(String specializationCode, String city, Pageable pageable);

    @Query(value = "SELECT * FROM COMPANIES WHERE CMP_ID IN (SELECT COMPANIES_CMP_ID FROM COMPANIES_SPECIALIZATIONS WHERE SPECIALIZATIONS_SPC_ID = (SELECT SPC_ID FROM SPECIALIZATIONS WHERE LOWER(SPC_CODE) = LOWER(?1))) AND NOT CMP_REMOVED", nativeQuery = true)
    Page<Company> findAllBySpecialization(String specializationCode, Pageable pageable);

    @Query(value = "SELECT * FROM COMPANIES WHERE CMP_ID IN (SELECT LOC_CMP_ID FROM LOCATIONS WHERE LOC_ADR_ID = (SELECT ADR_ID FROM ADDRESSES WHERE LOWER(ADR_CITY) = LOWER(?1)) AND NOT LOC_REMOVED) AND NOT CMP_REMOVED", nativeQuery = true)
    Page<Company> findAllByCity(String city, Pageable pageable);

}
