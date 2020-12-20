package com.piwko.booking.api.form.get;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetSpecializationsForm {

    private List<GetSpecializationForm> specializations;
}
