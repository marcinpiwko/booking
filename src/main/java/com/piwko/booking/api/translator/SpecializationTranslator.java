package com.piwko.booking.api.translator;

import com.piwko.booking.api.form.get.GetSpecializationForm;
import com.piwko.booking.persistence.model.Specialization;
import org.springframework.stereotype.Component;

@Component
public class SpecializationTranslator {

    public GetSpecializationForm translate(Specialization specialization) {
        GetSpecializationForm specializationForm = new GetSpecializationForm();
        specializationForm.setId(specialization.getId());
        specializationForm.setName(specialization.getName());
        specializationForm.setCode(specialization.getCode());
        return specializationForm;
    }
}
