package com.piwko.booking.persistence.repository;

import com.piwko.booking.persistence.model.VirtualUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VirtualUserRepository extends JpaRepository<VirtualUser, Long> {

    Optional<VirtualUser> findByEmail(String email);

    boolean existsByEmail(String email);
}
