package com.piwko.booking.service.interfaces;

import com.piwko.booking.persistence.model.Company;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import com.piwko.booking.util.exception.ResourceNotUniqueException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyService {

    Company getCompany(Long id) throws ResourceNotFoundException;

    Page<Company> getCompanies(Pageable pageable);

    Long createCompany(Company company) throws ResourceNotUniqueException;

    void modifyCompany(Long id, Company company) throws ResourceNotUniqueException, ResourceNotFoundException;

    void deleteCompany(Long id);
}
