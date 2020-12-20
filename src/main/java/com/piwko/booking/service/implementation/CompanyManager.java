package com.piwko.booking.service.implementation;

import com.piwko.booking.persistence.cache.Cache;
import com.piwko.booking.persistence.cache.GlobalCache;
import com.piwko.booking.persistence.model.Company;
import com.piwko.booking.persistence.repository.CompanyRepository;
import com.piwko.booking.persistence.search.CompanySearchCriteria;
import com.piwko.booking.service.interfaces.CompanyService;
import com.piwko.booking.persistence.EntityFactory;
import com.piwko.booking.util.CollectionsUtil;
import com.piwko.booking.util.StringUtil;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import com.piwko.booking.util.exception.ResourceNotUniqueException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyManager implements CompanyService {

    private final CompanyRepository companyRepository;

    private final Cache<Company> cache = GlobalCache.getInstance().getCompanies();

    private static final String ENTITY_NAME = EntityFactory.getEntityName(Company.class);

    @Override
    public Company getCompany(Long id) throws ResourceNotFoundException {
        Optional<Company> cachedCompany = cache.get(id);
        if (!cachedCompany.isPresent()) {
            Company company = companyRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(ENTITY_NAME, "id", id));
            initializeLazyProperties(company);
            cache.put(company);
            return company;
        }
        return cachedCompany.get();
    }

    @Override
    public Company getCompany(String code) throws ResourceNotFoundException {
        Optional<Company> cachedCompany = cache.get(code);
        if (!cachedCompany.isPresent()) {
            Company company = companyRepository.findByCode(code)
                    .orElseThrow(() -> new ResourceNotFoundException(ENTITY_NAME, "code", code));
            initializeLazyProperties(company);
            cache.put(company);
            return company;
        }
        return cachedCompany.get();
    }

    @Override
    public Page<Company> getCompanies(CompanySearchCriteria searchCriteria, Pageable pageable) {
        Page<Company> companies = getCompaniesForSpecificSearchCriteria(searchCriteria, pageable);
        companies.forEach(this::initializeLazyProperties);
        return companies;
    }

    @Override
    public Long createCompany(Company company) throws ResourceNotUniqueException {
        validateUniqueCodeName(companyRepository, company);
        companyRepository.save(company);
        return cache.put(company);
    }

    @Override
    public void updateCompany(Long id, Company company) throws ResourceNotFoundException, ResourceNotUniqueException {
        validateUniqueCodeName(companyRepository, company);
        Company existingCompany = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ENTITY_NAME, "id", id));
        if (!StringUtil.isEmpty(company.getCode())) {
            existingCompany.setCode(company.getCode());
        }
        if (!StringUtil.isEmpty(company.getName())) {
            existingCompany.setName(company.getName());
        }
        if (company.getCancellationTime() != null) {
            existingCompany.setCancellationTime(company.getCancellationTime());
        }
        if (!CollectionsUtil.isEmpty(company.getSpecializations())) {
            existingCompany.setSpecializations(company.getSpecializations());
        }
        companyRepository.save(existingCompany);
        cache.put(existingCompany);
    }

    @Override
    public void deleteCompany(Long id) throws ResourceNotFoundException {
        if (!cache.existsById(id)) {
            throw new ResourceNotFoundException(ENTITY_NAME, "id", id);
        }
        companyRepository.softDeleteById(id);
        cache.remove(id);
    }

    @Override
    public boolean existsBySpecialization(Long specializationId) {
        return cache.getAll().stream().flatMap(c -> c.getSpecializations().stream()).anyMatch(s -> s.getId().equals(specializationId));
    }

    private Page<Company> getCompaniesForSpecificSearchCriteria(CompanySearchCriteria searchCriteria, Pageable pageable) {
        if (!StringUtil.isEmpty(searchCriteria.getSpecializationCode())) {
            if (!StringUtil.isEmpty(searchCriteria.getCity())) {
                return companyRepository.findAllBySpecializationAndCity(searchCriteria.getSpecializationCode(), searchCriteria.getCity(), pageable);
            } else {
                return companyRepository.findAllBySpecialization(searchCriteria.getSpecializationCode(), pageable);
            }
        } else if (!StringUtil.isEmpty(searchCriteria.getCity())) {
            return companyRepository.findAllByCity(searchCriteria.getCity(), pageable);
        } else {
            return companyRepository.findAll(pageable);
        }
    }

    @Override
    @EventListener(ContextRefreshedEvent.class)
    public void refreshCache() {
        cache.clear();
        List<Company> companies = companyRepository.findAll();
        for (Company c : companies) {
            initializeLazyProperties(c);
            cache.put(c);
        }
    }

    private void initializeLazyProperties(Company company) {
        Hibernate.initialize(company.getLocations());
        Hibernate.initialize(company.getSpecializations());
        Hibernate.initialize(company.getServices());
    }
}
