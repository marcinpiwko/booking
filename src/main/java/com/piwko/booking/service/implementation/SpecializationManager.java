package com.piwko.booking.service.implementation;

import com.piwko.booking.persistence.model.Specialization;
import com.piwko.booking.persistence.repository.SpecializationRepository;
import com.piwko.booking.service.interfaces.SpecializationService;
import com.piwko.booking.persistence.EntityFactory;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import com.piwko.booking.util.exception.ResourceNotUniqueException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpecializationManager implements SpecializationService {

    private final SpecializationRepository specializationRepository;

    private static final String ENTITY_NAME = EntityFactory.getEntityName(Specialization.class);

    @Override
    public Specialization getSpecialization(Long id) throws ResourceNotFoundException {
        return specializationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY_NAME, id));
    }

    @Override
    public Page<Specialization> getSpecializations(Pageable pageable) {
        return specializationRepository.findAll(pageable);
    }

    @Override
    public Long createSpecialization(Specialization specialization) throws ResourceNotUniqueException {
        validate(specialization);
        return specializationRepository.save(specialization).getId();
    }

    @Override
    public void modifySpecialization(Long id, Specialization specialization) throws ResourceNotUniqueException, ResourceNotFoundException {
        validate(specialization);
        Specialization existingSpecialization = specializationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY_NAME, id));
        fillSpecializationProperties(existingSpecialization, specialization);
        specializationRepository.save(existingSpecialization);
    }

    @Override
    public void deleteSpecialization(Long id) {
        specializationRepository.deleteById(id);
    }

    private void fillSpecializationProperties(Specialization existingSpecialization, Specialization updatedSpecialization) {
        if (updatedSpecialization.getCode() != null) {
            existingSpecialization.setCode(updatedSpecialization.getCode());
        }
        if (updatedSpecialization.getName() != null) {
            existingSpecialization.setName(updatedSpecialization.getName());
        }
    }

    private void validate(Specialization specialization) throws ResourceNotUniqueException {
        if (specializationRepository.existsByName(specialization.getName())) {
            throw new ResourceNotUniqueException(ENTITY_NAME, "name", specialization.getName());
        }
        if (specializationRepository.existsByCode(specialization.getCode())) {
            throw new ResourceNotUniqueException(ENTITY_NAME, "code", specialization.getCode());
        }
    }
}
