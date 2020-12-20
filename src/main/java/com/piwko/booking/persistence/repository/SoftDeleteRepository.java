package com.piwko.booking.persistence.repository;

import com.piwko.booking.persistence.model.SoftDeleteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

@NoRepositoryBean
public interface SoftDeleteRepository<T extends SoftDeleteEntity, K> extends JpaRepository<T, K> {

    @Query("UPDATE #{#entityName} e SET e.removed = true WHERE e.id = ?1")
    @Transactional
    @Modifying
    void softDeleteById(Long id);

}
