package com.piwko.booking.api.form.interfaces;

public interface PostPatchService {

    String getCode();

    String getName();

    String getDescription();

    Double getPrice();

    boolean isAvailable();

    Integer getDuration();

    default String getCompanyCode() {
        return null;
    }
}
