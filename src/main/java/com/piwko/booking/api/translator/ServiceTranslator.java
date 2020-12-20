package com.piwko.booking.api.translator;

import com.piwko.booking.api.form.get.GetServiceForm;
import com.piwko.booking.api.form.interfaces.PostPatchService;
import com.piwko.booking.persistence.EntityFactory;
import com.piwko.booking.persistence.model.Service;
import com.piwko.booking.persistence.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ServiceTranslator {

    public GetServiceForm translate(Service service) {
        GetServiceForm serviceForm = new GetServiceForm();
        serviceForm.setCode(service.getCode());
        serviceForm.setName(service.getName());
        serviceForm.setAvailable(service.isAvailable());
        serviceForm.setDescription(service.getDescription());
        serviceForm.setDuration(service.getDuration());
        serviceForm.setId(service.getId());
        serviceForm.setPrice(service.getPrice());
        serviceForm.setCompanyCode(service.getCompany().getCode());
        return serviceForm;
    }

    public Service translate(PostPatchService serviceForm, Optional<User> user) {
        Service service = EntityFactory.newEntityInstance(Service.class);
        service.setCode(serviceForm.getCode());
        service.setName(serviceForm.getName());
        service.setPrice(serviceForm.getPrice());
        service.setDescription(serviceForm.getDescription());
        service.setAvailable(serviceForm.isAvailable());
        service.setDuration(serviceForm.getDuration());
        user.ifPresent(c -> service.setCompany(c.getCompany()));
        return service;
    }
}
