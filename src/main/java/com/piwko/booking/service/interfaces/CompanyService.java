package com.piwko.booking.service.interfaces;

import com.piwko.booking.persistence.model.Company;
import com.piwko.booking.persistence.search.CompanySearchCriteria;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import com.piwko.booking.util.exception.ResourceNotUniqueException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyService extends AbstractCodeNameService, CacheService {

    Company getCompany(Long id) throws ResourceNotFoundException;

    Company getCompany(String code) throws ResourceNotFoundException;

    Page<Company> getCompanies(CompanySearchCriteria searchCriteria, Pageable pageable);

    Long createCompany(Company company) throws ResourceNotUniqueException;

    void updateCompany(Long id, Company company) throws ResourceNotFoundException, ResourceNotUniqueException;

    void deleteCompany(Long id) throws ResourceNotFoundException;

    boolean existsBySpecialization(Long specializationId);
}
