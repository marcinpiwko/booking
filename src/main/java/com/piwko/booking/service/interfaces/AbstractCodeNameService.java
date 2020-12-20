package com.piwko.booking.service.interfaces;

import com.piwko.booking.persistence.model.CodeNameEntity;
import com.piwko.booking.persistence.model.Employee;
import com.piwko.booking.persistence.repository.CodeNameRepository;
import com.piwko.booking.util.exception.ResourceNotUniqueException;

public interface AbstractCodeNameService {

    default void validateUniqueCodeName(CodeNameRepository<? extends CodeNameEntity> repository, CodeNameEntity entity) throws ResourceNotUniqueException {
        if (repository.existsByCode(entity.getCode())) {
            throw new ResourceNotUniqueException(entity.getClass().getSimpleName(), "code", entity.getCode());
        }
        if (!(entity instanceof Employee) && repository.existsByName(entity.getName())) {
            throw new ResourceNotUniqueException(entity.getClass().getSimpleName(), "name", entity.getName());
        }
    }
}
