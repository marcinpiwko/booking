package com.piwko.booking.api.translator;

import com.piwko.booking.api.form.get.GetAddressForm;
import com.piwko.booking.api.form.interfaces.PostPatchAddress;
import com.piwko.booking.persistence.EntityFactory;
import com.piwko.booking.persistence.model.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressTranslator {

    public GetAddressForm translate(Address address) {
        GetAddressForm addressForm = new GetAddressForm();
        addressForm.setCity(address.getCity());
        addressForm.setCountry(address.getCountry());
        addressForm.setEmail(address.getEmail());
        addressForm.setPhoneNumber(address.getPhoneNumber());
        addressForm.setPostalCode(address.getPostalCode());
        addressForm.setStreet(address.getStreet());
        return addressForm;
    }

    public Address translate(PostPatchAddress addressForm) {
        Address address = EntityFactory.newEntityInstance(Address.class);
        address.setCity(addressForm.getCity());
        address.setStreet(addressForm.getStreet());
        address.setCountry(addressForm.getCountry());
        address.setPostalCode(addressForm.getPostalCode());
        address.setPhoneNumber(addressForm.getPhoneNumber());
        address.setEmail(addressForm.getEmail());
        return address;
    }
}
