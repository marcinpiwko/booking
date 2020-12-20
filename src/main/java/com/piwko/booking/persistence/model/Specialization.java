package com.piwko.booking.persistence.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity(name = "SPECIALIZATIONS")
@Table(name = "SPECIALIZATIONS")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Specialization implements CodeNameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SPECIALIZATION_ID_GENERATOR")
    @SequenceGenerator(name = "SPECIALIZATION_ID_GENERATOR", sequenceName = "SPC_SEQ")
    @Column(name = "SPC_ID", nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "SPC_CODE", nullable = false)
    @Size(min = 3, max = 10)
    private String code;

    @Column(name = "SPC_NAME", nullable = false)
    private String name;

}
