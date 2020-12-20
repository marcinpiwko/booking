package com.piwko.booking.api.translator;

import com.piwko.booking.api.form.interfaces.PostPatchUser;
import com.piwko.booking.api.form.get.GetUserForm;
import com.piwko.booking.persistence.EntityFactory;
import com.piwko.booking.persistence.model.User;
import com.piwko.booking.service.interfaces.CompanyService;
import com.piwko.booking.util.StringUtil;
import com.piwko.booking.util.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserTranslator {

    private final PasswordEncoder passwordEncoder;

    private final CompanyService companyService;

    private final CompanyTranslator companyTranslator;

    public GetUserForm translate(User user) {
        GetUserForm userForm = new GetUserForm();
        userForm.setFirstName(user.getFirstName());
        userForm.setLastName(user.getLastName());
        userForm.setBirthDate(user.getBirthDate());
        userForm.setEmail(user.getEmail());
        userForm.setPhoneNumber(user.getPhoneNumber());
        userForm.setGender(user.getGender());
        userForm.setId(user.getId());
        if (user.getCompany() != null) {
            userForm.setCompanyCode(user.getCompany().getCode());
        }
        return userForm;
    }

    public User translate(PostPatchUser userForm) throws ResourceNotFoundException {
        User user = EntityFactory.newEntityInstance(User.class);
        user.setFirstName(userForm.getFirstName());
        user.setLastName(userForm.getLastName());
        user.setEmail(userForm.getEmail());
        if (!StringUtil.isEmpty(userForm.getPassword())) {
            user.setPassword(passwordEncoder.encode(userForm.getPassword()));
        }
        user.setGender(userForm.getGender());
        user.setBirthDate(userForm.getBirthDate());
        user.setPhoneNumber(userForm.getPhoneNumber());
        if (!StringUtil.isEmpty(userForm.getCompanyCode())) {
            user.setCompany(companyService.getCompany(userForm.getCompanyCode()));
        }
        else if (userForm.getCompany() != null) {
            user.setCompany(companyTranslator.translate(userForm.getCompany()));
        }
        return user;
    }
}
