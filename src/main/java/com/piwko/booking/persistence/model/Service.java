package com.piwko.booking.persistence.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "SERVICES")
@Data
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SRV_ID", nullable = false)
    private Long id;

    @Column(name = "SRV_NAME", nullable = false)
    private String name;

    @Column(name = "SRV_CODE", nullable = false)
    private String code;

    @Column(name = "SRV_DESCRIPTION")
    private String description;

    @Column(name = "SRV_PRICE")
    private Double price;

    @Column(name = "SRV_AVAILABLE")
    private Boolean available;
}
