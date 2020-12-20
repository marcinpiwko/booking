package com.piwko.booking.api.translator;

import com.piwko.booking.api.form.get.GetSpecializationForm;
import com.piwko.booking.api.form.interfaces.PostPatchSpecialization;
import com.piwko.booking.persistence.EntityFactory;
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

    public Specialization translate(PostPatchSpecialization specializationForm) {
        Specialization specialization = EntityFactory.newEntityInstance(Specialization.class);
        specialization.setCode(specializationForm.getCode());
        specialization.setName(specializationForm.getName());
        return specialization;
    }
}
