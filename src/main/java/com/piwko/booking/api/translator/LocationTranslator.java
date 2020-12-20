package com.piwko.booking.api.translator;

import com.piwko.booking.api.form.get.GetLocationForm;
import com.piwko.booking.api.form.interfaces.PostPatchLocation;
import com.piwko.booking.persistence.EntityFactory;
import com.piwko.booking.persistence.model.Location;
import com.piwko.booking.persistence.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LocationTranslator {

    private final AddressTranslator addressTranslator;

    private final WorkingHoursTranslator workingHoursTranslator;

    public GetLocationForm translate(Location location) {
        GetLocationForm locationForm = new GetLocationForm();
        locationForm.setId(location.getId());
        locationForm.setCode(location.getCode());
        locationForm.setName(location.getName());
        locationForm.setCompanyCode(location.getCompany().getCode());
        locationForm.setAddress(addressTranslator.translate(location.getAddress()));
        locationForm.setWorkingHours(workingHoursTranslator.translate(location.getWorkingHours()));
        return locationForm;
    }

    public Location translate(PostPatchLocation locationForm, Optional<User> user) {
        Location location = EntityFactory.newEntityInstance(Location.class);
        location.setCode(locationForm.getCode());
        location.setName(locationForm.getName());
        location.setAddress(addressTranslator.translate(locationForm.getAddress()));
        user.ifPresent(value -> location.setCompany(value.getCompany()));
        location.setWorkingHours(workingHoursTranslator.translate(locationForm.getWorkingHours()));
        return location;
    }
}

