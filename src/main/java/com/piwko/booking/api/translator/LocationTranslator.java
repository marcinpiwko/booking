package com.piwko.booking.api.translator;

import com.piwko.booking.api.form.get.GetLocationForm;
import com.piwko.booking.persistence.model.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocationTranslator {

    private final AddressTranslator addressTranslator;

    public GetLocationForm translate(Location location) {
        GetLocationForm locationForm = new GetLocationForm();
        locationForm.setId(location.getId());
        locationForm.setCode(location.getCode());
        locationForm.setName(location.getName());
        locationForm.setAddress(addressTranslator.translate(location.getAddress()));
        return locationForm;
    }
}

