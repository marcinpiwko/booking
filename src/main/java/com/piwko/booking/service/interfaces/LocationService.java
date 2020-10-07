package com.piwko.booking.service.interfaces;

import com.piwko.booking.persistence.model.Location;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import com.piwko.booking.util.exception.ResourceNotUniqueException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LocationService {

    Location getLocation(Long id) throws ResourceNotFoundException;

    Page<Location> getLocations(Pageable pageable);

    Long createLocation(Location company) throws ResourceNotUniqueException;

    void modifyLocation(Long id, Location company) throws ResourceNotUniqueException, ResourceNotFoundException;

    void deleteLocation(Long id);
}
