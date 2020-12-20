package com.piwko.booking.persistence.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Entity(name = "ADDRESSES")
@Table(name = "ADDRESSES")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Address implements IdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ADDRESS_ID_GENERATOR")
    @SequenceGenerator(name = "ADDRESS_ID_GENERATOR", sequenceName = "ADR_SEQ")
    @Column(name = "ADR_ID", nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "ADR_STREET", nullable = false)
    private String street;

    @Column(name = "ADR_CITY", nullable = false)
    private String city;

    @Column(name = "ADR_COUNTRY", nullable = false)
    private String country;

    @Column(name = "ADR_POSTAL_CODE", nullable = false)
    @Pattern(regexp = "[0-9]{2}-[0-9]{3}")
    private String postalCode;

    @Column(name = "ADR_PHONE_NUMBER")
    @Pattern(regexp = "[0-9]{9}")
    private String phoneNumber;

    @Column(name = "ADR_EMAIL")
    @Email
    private String email;
}
