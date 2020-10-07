package com.piwko.booking.service.implementation;

import com.piwko.booking.persistence.JPAUtil;
import com.piwko.booking.persistence.model.Company;
import com.piwko.booking.persistence.repository.CompanyRepository;
import com.piwko.booking.service.interfaces.CompanyService;
import com.piwko.booking.persistence.EntityFactory;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import com.piwko.booking.util.exception.ResourceNotUniqueException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyManager implements CompanyService {

    private final CompanyRepository companyRepository;

    private static final String ENTITY_NAME = EntityFactory.getEntityName(Company.class);

    @Override
    @Transactional(readOnly = true)
    public Company getCompany(Long id) throws ResourceNotFoundException {
        Company company = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ENTITY_NAME, id));
        JPAUtil.initializeLazyProperties(company);
        return company;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Company> getCompanies(Pageable pageable) {
        Page<Company> companies = companyRepository.findAll(pageable);
        companies.getContent().forEach(JPAUtil::initializeLazyProperties);
        return companies;
    }

    @Override
    public Long createCompany(Company company) throws ResourceNotUniqueException {
        validate(company);
        return companyRepository.save(company).getId();
    }

    @Override
    public void modifyCompany(Long id, Company company) throws ResourceNotFoundException, ResourceNotUniqueException {
        validate(company);
        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY_NAME, id));
        fillCompanyProperties(existingCompany, company);
        companyRepository.save(existingCompany);
    }

    @Override
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    private void fillCompanyProperties(Company existingCompany, Company updatedCompany) {
        if (updatedCompany.getCode() != null) {
            existingCompany.setCode(updatedCompany.getCode());
        }
        if (updatedCompany.getName() != null) {
            existingCompany.setName(updatedCompany.getName());
        }
    }

    private void validate(Company company) throws ResourceNotUniqueException {
        if (companyRepository.existsByName(company.getName())) {
            throw new ResourceNotUniqueException(ENTITY_NAME, "name", company.getName());
        }
        if (companyRepository.existsByCode(company.getCode())) {
            throw new ResourceNotUniqueException(ENTITY_NAME, "code", company.getCode());
        }
    }
}
