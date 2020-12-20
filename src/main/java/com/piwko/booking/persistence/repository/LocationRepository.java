package com.piwko.booking.persistence.repository;

import com.piwko.booking.persistence.model.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends SoftDeleteRepository<Location, Long>, CodeNameRepository<Location> {

    @Query(value = "SELECT * FROM LOCATIONS WHERE LOC_CMP_ID = (SELECT CMP_ID FROM COMPANIES WHERE LOWER(CMP_CODE) = LOWER(?1) AND NOT CMP_REMOVED) AND NOT LOC_REMOVED", nativeQuery = true)
    Page<Location> findAllByCompanyCode(String companyCode, Pageable pageable);

    @Query(value = "SELECT CASE WHEN COUNT(l) > 0 THEN TRUE ELSE FALSE END FROM LOCATIONS l WHERE LOWER(LOC_CODE) = LOWER(?1) " +
            "AND LOC_CMP_ID = (SELECT CMP_ID FROM COMPANIES WHERE LOWER(CMP_CODE) = LOWER(?2))", nativeQuery = true)
    boolean checkIfLocationBelongsToCompany(String locationCode, String companyCode);

    @Query(value = "SELECT CASE WHEN COUNT(l) > 0 THEN TRUE ELSE FALSE END FROM LOCATIONS l WHERE LOC_ID = ?1 " +
            "AND LOC_CMP_ID = ?2", nativeQuery = true)
    boolean checkIfLocationBelongsToCompany(Long locationId, Long companyId);

    @Query(value = "SELECT * FROM LOCATIONS WHERE LOWER(LOC_CODE) = LOWER(?1) " +
            "AND LOC_CMP_ID = (SELECT CMP_ID FROM COMPANIES WHERE LOWER(CMP_CODE) = LOWER(?2) AND NOT CMP_REMOVED) AND NOT LOC_REMOVED", nativeQuery = true)
    Optional<Location> findByLocationCodeAndCompanyCode(String locationCode, String companyCode);

}
