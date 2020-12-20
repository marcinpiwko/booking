package com.piwko.booking.api.form.interfaces;

import com.piwko.booking.api.form.post.PostCompanyForm;

import java.time.LocalDate;

public interface PostPatchUser {

    String getEmail();

    String getPassword();

    String getPhoneNumber();

    default String getFirstName() {
        return null;
    }

    default String getLastName() {
        return null;
    }

    default String getCompanyCode() {
        return null;
    }

    default PostCompanyForm getCompany() {
        return null;
    }

    default LocalDate getBirthDate() {
        return null;
    }

    default String getGender() {
        return null;
    }
}
