package com.piwko.booking.persistence.repository;

import com.piwko.booking.persistence.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends SoftDeleteRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Page<User> findAllByEnabled(boolean enabled, Pageable pageable);

    @Query(value = "SELECT * FROM USERS WHERE USR_ENABLED = ?1 AND USR_CMP_ID = " +
            "(SELECT CMP_ID FROM COMPANIES WHERE LOWER(CMP_CODE) = LOWER(?2) AND NOT CMP_REMOVED) AND NOT USR_REMOVED", nativeQuery = true)
    Page<User> findAllByEnabledAndCompanyCode(boolean enabled, String companyCode, Pageable pageable);

    @Query(value = "SELECT * FROM USERS WHERE USR_CMP_ID = " +
            "(SELECT CMP_ID FROM COMPANIES WHERE LOWER(CMP_CODE) = LOWER(?1) AND NOT CMP_REMOVED) AND NOT USR_REMOVED", nativeQuery = true)
    Page<User> findAllByCompanyCode(String companyCode, Pageable pageable);

    @Query(value = "UPDATE USERS SET USR_ENABLED = TRUE WHERE USR_ID = ?1", nativeQuery = true)
    @Modifying
    void enableUser(Long id);

}
