package com.piwko.booking.api.form.interfaces;

import java.util.List;

public interface PostPatchEmployee {

    String getFirstName();

    String getLastName();

    String getCode();

    String getLocationCode();

    PostPatchWorkingHours getWorkingHours();

    List<String> getServiceCodes();

}
