package com.piwko.booking.api.form.get;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@AllArgsConstructor
public class GetServicesForm {

    private Page<GetServiceForm> services;
}
