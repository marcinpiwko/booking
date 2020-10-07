package com.piwko.booking.api.form.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCompaniesForm {

    private Page<GetCompanyForm> companies;
}
