package com.piwko.booking.persistence.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "SPECIALIZATIONS")
@Data
public class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SPC_ID", nullable = false)
    private Long id;

    @Column(name = "SPC_NAME", nullable = false)
    private String name;

    @Column(name = "SPC_CODE", nullable = false)
    private String code;

    @OneToMany(mappedBy = "parentSpecialization", cascade = CascadeType.ALL)
    private List<SubSpecialization> subSpecializations;
}
