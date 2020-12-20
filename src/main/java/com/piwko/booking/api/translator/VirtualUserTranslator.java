package com.piwko.booking.api.translator;

import com.piwko.booking.api.form.post.PostVirtualUserForm;
import com.piwko.booking.persistence.EntityFactory;
import com.piwko.booking.persistence.model.VirtualUser;
import org.springframework.stereotype.Component;

@Component
public class VirtualUserTranslator {

    public VirtualUser translate(PostVirtualUserForm virtualUserForm) {
        VirtualUser virtualUser = EntityFactory.newEntityInstance(VirtualUser.class);
        virtualUser.setBirthDate(virtualUserForm.getBirthDate());
        virtualUser.setEmail(virtualUserForm.getEmail());
        virtualUser.setFirstName(virtualUserForm.getFirstName());
        virtualUser.setLastName(virtualUserForm.getLastName());
        virtualUser.setPhoneNumber(virtualUserForm.getPhoneNumber());
        virtualUser.setGender(virtualUserForm.getGender());
        return virtualUser;
    }
}
