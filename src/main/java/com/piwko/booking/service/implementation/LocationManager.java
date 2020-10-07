package com.piwko.booking.service.implementation;

import com.piwko.booking.persistence.model.Location;
import com.piwko.booking.service.interfaces.LocationService;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import com.piwko.booking.util.exception.ResourceNotUniqueException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LocationManager implements LocationService {

    @Override
    public Location getLocation(Long id) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public Page<Location> getLocations(Pageable pageable) {
        return null;
    }

    @Override
    public Long createLocation(Location company) throws ResourceNotUniqueException {
        return null;
    }

    @Override
    public void modifyLocation(Long id, Location company) throws ResourceNotUniqueException, ResourceNotFoundException {

    }

    @Override
    public void deleteLocation(Long id) {

    }
}
