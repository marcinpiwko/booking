package com.piwko.booking.persistence.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "SUB_SPECIALIZATIONS")
@Data
public class SubSpecialization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUB_ID", nullable = false)
    private Long id;

    @Column(name = "SUB_NAME", nullable = false)
    private String name;

    @ManyToOne()
    @JoinColumn(referencedColumnName = "SPC_ID", name = "SUB_SPC_ID", nullable = false)
    private Specialization parentSpecialization;
}
