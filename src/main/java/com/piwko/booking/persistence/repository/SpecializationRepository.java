package com.piwko.booking.persistence.repository;

import com.piwko.booking.persistence.model.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Long>, CodeNameRepository<Specialization> {

    @Query(value = "DELETE FROM COMPANIES_SPECIALIZATIONS WHERE SPECIALIZATIONS_SPC_ID = ?1", nativeQuery = true)
    @Modifying
    void clearCompaniesSpecialization(Long id);

}
