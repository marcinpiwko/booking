package com.piwko.booking.persistence.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceSearchCriteria {

    private String companyCode;

    private String locationCode;

    private String employee;
}
