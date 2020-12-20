package com.piwko.booking.api.form.interfaces;

public interface PostPatchLocation {

    String getCode();

    String getName();

    PostPatchAddress getAddress();

    PostPatchWorkingHours getWorkingHours();
}
