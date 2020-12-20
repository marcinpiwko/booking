package com.piwko.booking.service.implementation;

import com.piwko.booking.persistence.cache.Cache;
import com.piwko.booking.persistence.cache.GlobalCache;
import com.piwko.booking.persistence.model.Specialization;
import com.piwko.booking.persistence.repository.SpecializationRepository;
import com.piwko.booking.service.interfaces.CompanyService;
import com.piwko.booking.service.interfaces.SpecializationService;
import com.piwko.booking.persistence.EntityFactory;
import com.piwko.booking.util.StringUtil;
import com.piwko.booking.util.exception.ResourceInUseException;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import com.piwko.booking.util.exception.ResourceNotUniqueException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SpecializationManager implements SpecializationService {

    private final SpecializationRepository specializationRepository;

    private final Cache<Specialization> cache = GlobalCache.getInstance().getSpecializations();

    private final CompanyService companyService;

    private static final String ENTITY_NAME = EntityFactory.getEntityName(Specialization.class);

    @Override
    public Specialization getSpecialization(Long id) throws ResourceNotFoundException {
        Optional<Specialization> cachedSpecialization = cache.get(id);
        if (!cachedSpecialization.isPresent()) {
            return specializationRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(ENTITY_NAME, "id", id));
        }
        return cachedSpecialization.get();
    }

    @Override
    public Specialization getSpecialization(String code) throws ResourceNotFoundException {
        Optional<Specialization> cachedSpecialization = cache.get(code);
        if (!cachedSpecialization.isPresent()) {
            return specializationRepository.findByCode(code)
                    .orElseThrow(() -> new ResourceNotFoundException(ENTITY_NAME, "code", code));
        }
        return cachedSpecialization.get();
    }

    @Override
    public List<Specialization> getSpecializations(Sort sort) {
        return specializationRepository.findAll(sort);
    }

    @Override
    public Long createSpecialization(Specialization specialization) throws ResourceNotUniqueException {
        validateUniqueCodeName(specializationRepository, specialization);
        specializationRepository.save(specialization);
        return cache.put(specialization);
    }

    @Override
    public void updateSpecialization(Long id, Specialization specialization) throws ResourceNotFoundException, ResourceNotUniqueException {
        validateUniqueCodeName(specializationRepository, specialization);
        Specialization existingSpecialization = specializationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY_NAME, "id", id));
        if (!StringUtil.isEmpty(specialization.getCode())) {
            existingSpecialization.setCode(specialization.getCode());
        }
        if (!StringUtil.isEmpty(specialization.getName())) {
            existingSpecialization.setName(specialization.getName());
        }
        specializationRepository.save(existingSpecialization);
        cache.put(existingSpecialization);
    }

    @Override
    public void deleteSpecialization(Long id) throws ResourceInUseException, ResourceNotFoundException {
        if (companyService.existsBySpecialization(id)) {
            throw new ResourceInUseException(ENTITY_NAME, id);
        }
        if (!specializationRepository.existsById(id)) {
            throw new ResourceNotFoundException(ENTITY_NAME, "id", id);
        }
        specializationRepository.clearCompaniesSpecialization(id);
        specializationRepository.deleteById(id);
        cache.remove(id);
    }

    @Override
    @EventListener(ContextRefreshedEvent.class)
    public void refreshCache() {
        cache.clear();
        specializationRepository.findAll().forEach(cache::put);
    }
}
