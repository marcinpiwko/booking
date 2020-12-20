package com.piwko.booking.service.interfaces;

import com.piwko.booking.persistence.model.Location;
import com.piwko.booking.persistence.search.LocationSearchCriteria;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import com.piwko.booking.util.exception.ResourceNotUniqueException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LocationService extends AbstractCodeNameService, CacheService {

    Location getLocation(Long id) throws ResourceNotFoundException;

    Location getLocation(String code) throws ResourceNotFoundException;

    Location getLocationByCodeAndCompanyCode(String locationCode, String companyCode) throws ResourceNotFoundException;

    Page<Location> getLocations(LocationSearchCriteria searchCriteria, Pageable pageable);

    Long createLocation(Location location) throws ResourceNotUniqueException;

    void createLocations(List<Location> locations) throws ResourceNotUniqueException;

    void updateLocation(Long id, Location location) throws ResourceNotFoundException, ResourceNotUniqueException;

    void deleteLocation(Long id) throws ResourceNotFoundException;

    boolean existsByCompany(String locationCode, String companyCode);

    boolean existsByCompany(Long locationId, Long companyId);
}
