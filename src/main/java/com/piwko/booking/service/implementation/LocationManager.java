package com.piwko.booking.service.implementation;

import com.piwko.booking.persistence.EntityFactory;
import com.piwko.booking.persistence.cache.Cache;
import com.piwko.booking.persistence.cache.GlobalCache;
import com.piwko.booking.persistence.model.Address;
import com.piwko.booking.persistence.model.Location;
import com.piwko.booking.persistence.repository.LocationRepository;
import com.piwko.booking.persistence.search.LocationSearchCriteria;
import com.piwko.booking.service.interfaces.LocationService;
import com.piwko.booking.util.StringUtil;
import com.piwko.booking.util.WorkingHoursUtil;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import com.piwko.booking.util.exception.ResourceNotUniqueException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LocationManager implements LocationService {

    private final LocationRepository locationRepository;

    private final Cache<Location> cache = GlobalCache.getInstance().getLocations();

    private static final String ENTITY_NAME = EntityFactory.getEntityName(Location.class);

    @Override
    public Location getLocation(Long id) throws ResourceNotFoundException {
        Optional<Location> cachedLocation = cache.get(id);
        if (!cachedLocation.isPresent()) {
            return locationRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(ENTITY_NAME, "id", id));
        }
        return cachedLocation.get();
    }

    @Override
    public Location getLocation(String code) throws ResourceNotFoundException {
        Optional<Location> cachedLocation = cache.get(code);
        if (!cachedLocation.isPresent()) {
            return locationRepository.findByCode(code)
                    .orElseThrow(() -> new ResourceNotFoundException(ENTITY_NAME, "code", code));
        }
        return cachedLocation.get();
    }

    @Override
    public Page<Location> getLocations(LocationSearchCriteria searchCriteria, Pageable pageable) {
        if (!StringUtil.isEmpty(searchCriteria.getCompanyCode())) {
            return locationRepository.findAllByCompanyCode(searchCriteria.getCompanyCode(), pageable);
        }
        return locationRepository.findAll(pageable);
    }

    @Override
    public Long createLocation(Location location) throws ResourceNotUniqueException {
        validateUniqueCodeName(locationRepository, location);
        locationRepository.save(location);
        return cache.put(location);
    }

    @Override
    public void createLocations(List<Location> locations) throws ResourceNotUniqueException {
        for (Location location : locations) {
            validateUniqueCodeName(locationRepository, location);
        }
        locationRepository.saveAll(locations);
        locations.forEach(cache::put);
    }

    @Override
    public void updateLocation(Long id, Location location) throws ResourceNotFoundException, ResourceNotUniqueException {
        validateUniqueCodeName(locationRepository, location);
        Location existingLocation = locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY_NAME, "id", id));
        if (!StringUtil.isEmpty(location.getCode())) {
            existingLocation.setCode(location.getCode());
        }
        if (!StringUtil.isEmpty(location.getName())) {
            existingLocation.setName(location.getName());
        }
        if (location.getAddress() != null) {
            updateAddress(existingLocation.getAddress(), location.getAddress());
        }
        if (location.getWorkingHours() != null) {
            WorkingHoursUtil.updateWorkingHours(existingLocation.getWorkingHours(), location.getWorkingHours());
        }
        locationRepository.save(existingLocation);
        cache.put(existingLocation);
    }

    @Override
    public void deleteLocation(Long id) throws ResourceNotFoundException {
        if (!locationRepository.existsById(id)) {
            throw new ResourceNotFoundException(ENTITY_NAME, "id", id);
        }
        locationRepository.softDeleteById(id);
        cache.remove(id);
    }

    @Override
    public boolean existsByCompany(String locationCode, String companyCode) {
        return locationRepository.checkIfLocationBelongsToCompany(locationCode, companyCode);
    }

    @Override
    public boolean existsByCompany(Long locationId, Long companyId) {
        return locationRepository.checkIfLocationBelongsToCompany(locationId, companyId);
    }

    @Override
    public Location getLocationByCodeAndCompanyCode(String locationCode, String companyCode) throws ResourceNotFoundException {
        return locationRepository.findByLocationCodeAndCompanyCode(locationCode, companyCode)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY_NAME, "code", locationCode));
    }

    private void updateAddress(Address existingAddress, Address address) {
        if (!StringUtil.isEmpty(address.getCity())) {
            existingAddress.setCity(address.getCity());
        }
        if (!StringUtil.isEmpty(address.getCountry())) {
            existingAddress.setCountry(address.getCountry());
        }
        if (!StringUtil.isEmpty(address.getEmail())) {
            existingAddress.setEmail(address.getEmail());
        }
        if (!StringUtil.isEmpty(address.getStreet())) {
            existingAddress.setStreet(address.getStreet());
        }
        if (!StringUtil.isEmpty(address.getPhoneNumber())) {
            existingAddress.setPhoneNumber(address.getPhoneNumber());
        }
        if (!StringUtil.isEmpty(address.getPostalCode())) {
            existingAddress.setPostalCode(address.getPostalCode());
        }
    }

    @Override
    @EventListener(ContextRefreshedEvent.class)
    public void refreshCache() {
        cache.clear();
        locationRepository.findAll().forEach(cache::put);
    }
}
