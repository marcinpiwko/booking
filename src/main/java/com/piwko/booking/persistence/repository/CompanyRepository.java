package com.piwko.booking.persistence.repository;

import com.piwko.booking.persistence.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
