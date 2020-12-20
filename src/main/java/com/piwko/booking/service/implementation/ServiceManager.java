package com.piwko.booking.service.implementation;

import com.piwko.booking.persistence.EntityFactory;
import com.piwko.booking.persistence.cache.Cache;
import com.piwko.booking.persistence.cache.GlobalCache;
import com.piwko.booking.persistence.model.Service;
import com.piwko.booking.persistence.repository.ServiceRepository;
import com.piwko.booking.persistence.search.ServiceSearchCriteria;
import com.piwko.booking.service.interfaces.ServiceService;
import com.piwko.booking.util.StringUtil;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import com.piwko.booking.util.exception.ResourceNotUniqueException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceManager implements ServiceService {

    private final ServiceRepository serviceRepository;

    private final Cache<Service> cache = GlobalCache.getInstance().getServices();

    private static final String ENTITY_NAME = EntityFactory.getEntityName(Service.class);

    @Override
    public Service getService(Long id) throws ResourceNotFoundException {
        Optional<Service> cachedService = cache.get(id);
        if (!cachedService.isPresent()) {
            return serviceRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(ENTITY_NAME, "id", id));
        }
        return cachedService.get();
    }

    @Override
    public Service getService(String code) throws ResourceNotFoundException {
        Optional<Service> cachedService = cache.get(code);
        if (!cachedService.isPresent()) {
            return serviceRepository.findByCode(code)
                    .orElseThrow(() -> new ResourceNotFoundException(ENTITY_NAME, "code", code));
        }
        return cachedService.get();
    }

    @Override
    public Page<Service> getServices(ServiceSearchCriteria searchCriteria, Pageable pageable) {
        if (!StringUtil.isEmpty(searchCriteria.getCompanyCode())) {
            return serviceRepository.findAllByCompanyCode(searchCriteria.getCompanyCode(), pageable);
        }
        if (!StringUtil.isEmpty(searchCriteria.getLocationCode())) {
            return serviceRepository.findAllByLocationCode(searchCriteria.getLocationCode(), pageable);
        }
        if (!StringUtil.isEmpty(searchCriteria.getEmployee())) {
            String[] employeeSplit = searchCriteria.getEmployee().split(" ");
            return serviceRepository.findAllByEmployee(employeeSplit[0], employeeSplit[1], pageable);
        }
        return serviceRepository.findAll(pageable);
    }

    @Override
    public Long createService(Service service) throws ResourceNotUniqueException {
        validateUniqueCodeName(serviceRepository, service);
        serviceRepository.save(service);
        return cache.put(service);
    }

    @Override
    public void createServices(List<Service> services) throws ResourceNotUniqueException {
        for (Service service : services) {
            validateUniqueCodeName(serviceRepository, service);
        }
        serviceRepository.saveAll(services);
        services.forEach(cache::put);
    }

    @Override
    public void updateService(Long id, Service service) throws ResourceNotFoundException, ResourceNotUniqueException {
        validateUniqueCodeName(serviceRepository, service);
        Service existingService = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY_NAME, "id", id));
        if (!StringUtil.isEmpty(service.getCode())) {
            existingService.setCode(service.getCode());
        }
        if (!StringUtil.isEmpty(service.getName())) {
            existingService.setName(service.getName());
        }
        if (!StringUtil.isEmpty(service.getDescription())) {
            existingService.setDescription(service.getDescription());
        }
        if (service.getDuration() != null) {
            existingService.setDuration(service.getDuration());
        }
        if (service.getPrice() != null) {
            existingService.setPrice(service.getPrice());
        }
        existingService.setAvailable(service.isAvailable());
        serviceRepository.save(existingService);
        cache.put(existingService);
    }

    @Override
    public void deleteService(Long id) throws ResourceNotFoundException {
        if (!serviceRepository.existsById(id)) {
            throw new ResourceNotFoundException(ENTITY_NAME, "id", id);
        }
        serviceRepository.softDeleteById(id);
        cache.remove(id);
    }

    @Override
    public boolean existsByCompany(Long companyId) {
        return serviceRepository.existsByCompanyId(companyId);
    }

    @Override
    public Service getServiceByCodeAndCompanyCode(String serviceCode, String companyCode) throws ResourceNotFoundException {
        return serviceRepository.findByCodeAndCompanyCode(serviceCode, companyCode)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY_NAME, "code", serviceCode));
    }

    @Override
    @EventListener(ContextRefreshedEvent.class)
    public void refreshCache() {
        cache.clear();
        serviceRepository.findAll().forEach(cache::put);
    }
}
