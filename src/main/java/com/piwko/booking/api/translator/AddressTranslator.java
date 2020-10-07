package com.piwko.booking.api.translator;

import com.piwko.booking.api.form.get.GetAddressForm;
import com.piwko.booking.persistence.model.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressTranslator {

    public GetAddressForm translate(Address address) {
        GetAddressForm addressForm = new GetAddressForm();
        addressForm.setId(address.getId());
        addressForm.setCity(address.getCity());
        addressForm.setCountry(address.getCountry());
        addressForm.setEmail(address.getEmail());
        addressForm.setPhoneNumber(address.getPhoneNumber());
        addressForm.setPostalCode(address.getPostalCode());
        addressForm.setStreet(address.getStreet());
        return addressForm;
    }
}
