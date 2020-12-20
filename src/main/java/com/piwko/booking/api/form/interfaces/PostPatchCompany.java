package com.piwko.booking.api.form.interfaces;

import java.util.List;

public interface PostPatchCompany {

    String getCode();

    String getName();

    Integer getCancellationTime();

    List<String> getSpecializationCodes();
}
