package com.piwko.booking.persistence.repository;

public interface CodeNameRepository {

    boolean existsByName(String name);

    boolean existsByCode(String code);
}
