package com.piwko.booking.persistence.repository;

import com.piwko.booking.persistence.model.CodeNameEntity;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface CodeNameRepository<T extends CodeNameEntity> {

    boolean existsByCode(String code);

    boolean existsByName(String name);

    Optional<T> findByCode(String code);

    Optional<T> findByName(String name);

}
