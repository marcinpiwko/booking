package com.piwko.booking.service.imports.translator;

import com.piwko.booking.persistence.EntityFactory;
import com.piwko.booking.persistence.model.Address;
import com.piwko.booking.persistence.model.Company;
import com.piwko.booking.persistence.model.Location;
import com.piwko.booking.service.imports.dto.LocationCsv;
import com.piwko.booking.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
@Component
@RequiredArgsConstructor
public class LocationCsvTranslator {

    private final WorkingHoursCsvTranslator workingHoursCsvTranslator;

    public Location translate(@Valid LocationCsv locationCsv, Company company) {
        Location location = EntityFactory.newEntityInstance(Location.class);
        location.setName(locationCsv.getName());
        location.setCode(locationCsv.getCode());
        location.setCompany(company);
        location.setAddress(createNewAddress(locationCsv));
        location.setWorkingHours(workingHoursCsvTranslator.translate(locationCsv.getWorkingHours()));
        return location;
    }

    private Address createNewAddress(LocationCsv locationCsv) {
        Address address = EntityFactory.newEntityInstance(Address.class);
        address.setEmail(StringUtil.getNullOrNonEmptyString(locationCsv.getEmail()));
        address.setStreet(locationCsv.getStreet());
        address.setPhoneNumber(StringUtil.getNullOrNonEmptyString(locationCsv.getPhoneNumber()));
        address.setPostalCode(locationCsv.getPostalCode());
        address.setCountry(locationCsv.getCountry());
        address.setCity(locationCsv.getCity());
        return address;
    }
}
