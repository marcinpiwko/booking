package com.piwko.booking.persistence.repository;

import com.piwko.booking.persistence.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>, CodeNameRepository {

}
