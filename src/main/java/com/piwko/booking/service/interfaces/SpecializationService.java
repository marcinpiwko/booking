package com.piwko.booking.service.interfaces;

import com.piwko.booking.persistence.model.Specialization;
import com.piwko.booking.util.exception.ResourceInUseException;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import com.piwko.booking.util.exception.ResourceNotUniqueException;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface SpecializationService extends AbstractCodeNameService, CacheService {

    Specialization getSpecialization(Long id) throws ResourceNotFoundException;

    Specialization getSpecialization(String code) throws ResourceNotFoundException;

    List<Specialization> getSpecializations(Sort sort);

    Long createSpecialization(Specialization specialization) throws ResourceNotUniqueException;

    void updateSpecialization(Long id, Specialization specialization) throws ResourceNotFoundException, ResourceNotUniqueException;

    void deleteSpecialization(Long id) throws ResourceInUseException, ResourceNotFoundException;
}
