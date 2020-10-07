package com.piwko.booking.service.interfaces;

import com.piwko.booking.persistence.model.Specialization;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import com.piwko.booking.util.exception.ResourceNotUniqueException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SpecializationService {

    Specialization getSpecialization(Long id) throws ResourceNotFoundException;

    Page<Specialization> getSpecializations(Pageable pageable);

    Long createSpecialization(Specialization specialization) throws ResourceNotUniqueException;

    void modifySpecialization(Long id, Specialization specialization) throws ResourceNotUniqueException, ResourceNotFoundException;

    void deleteSpecialization(Long id);
}
