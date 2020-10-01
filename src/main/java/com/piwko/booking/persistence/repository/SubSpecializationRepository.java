package com.piwko.booking.persistence.repository;

import com.piwko.booking.persistence.model.SubSpecialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubSpecializationRepository extends JpaRepository<SubSpecialization, Long> {
}
