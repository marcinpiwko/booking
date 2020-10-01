package com.piwko.booking.persistence.repository;

import com.piwko.booking.persistence.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
