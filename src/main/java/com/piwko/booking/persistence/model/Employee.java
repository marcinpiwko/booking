package com.piwko.booking.persistence.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "EMPLOYEES")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMP_ID", nullable = false)
    private Long id;

    @Column(name = "EMP_FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "EMP_LAST_NAME", nullable = false)
    private String lastName;

    @OneToOne
    @JoinColumn(referencedColumnName = "LOC_ID", name = "EMP_LOC_ID", nullable = false)
    private Location location;

    @OneToMany
    private List<Service> services;
}
