package com.piwko.booking.persistence.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "ADDRESSES")
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADR_ID", nullable = false)
    private Long id;

    @Column(name = "ADR_STREET")
    private String street;

    @Column(name = "ADR_CITY")
    private String city;

    @Column(name = "ADR_COUNTRY")
    private String country;

    @Column(name = "ADR_POSTAL_CODE")
    private String postalCode;

    @Column(name = "ADR_PHONE_NUMBER", nullable = false)
    private String phoneNumber;

    @Column(name = "ADR_EMAIL", nullable = false)
    private String email;
}
