package com.piwko.booking.persistence.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "LOCATIONS")
@Data
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOC_ID", nullable = false)
    private Long id;

    @Column(name = "LOC_CODE", nullable = false)
    private String code;

    @Column(name = "LOC_NAME", nullable = false)
    private String name;

    @ManyToOne()
    @JoinColumn(referencedColumnName = "CMP_ID", name = "LOC_CMP_ID", nullable = false)
    private Company company;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "ADR_ID", name = "LOC_ADR_ID", nullable = false)
    private Address address;

}
