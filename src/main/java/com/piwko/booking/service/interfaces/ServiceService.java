package com.piwko.booking.service.interfaces;

import com.piwko.booking.persistence.model.Service;
import com.piwko.booking.persistence.search.ServiceSearchCriteria;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import com.piwko.booking.util.exception.ResourceNotUniqueException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ServiceService extends AbstractCodeNameService, CacheService {

    Service getService(Long id) throws ResourceNotFoundException;

    Service getService(String code) throws ResourceNotFoundException;

    Service getServiceByCodeAndCompanyCode(String serviceCode, String companyCode) throws ResourceNotFoundException;

    Page<Service> getServices(ServiceSearchCriteria searchCriteria, Pageable pageable);

    Long createService(Service service) throws ResourceNotUniqueException;

    void createServices(List<Service> services) throws ResourceNotUniqueException;

    void updateService(Long id, Service service) throws ResourceNotFoundException, ResourceNotUniqueException;

    void deleteService(Long id) throws ResourceNotFoundException;

    boolean existsByCompany(Long companyId);
}
