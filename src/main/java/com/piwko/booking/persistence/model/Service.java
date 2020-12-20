package com.piwko.booking.persistence.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity(name = "SERVICES")
@Table(name = "SERVICES")
@Where(clause = "NOT SRV_REMOVED")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Service implements CodeNameEntity, SoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SERVICE_ID_GENERATOR")
    @SequenceGenerator(name = "SERVICE_ID_GENERATOR", sequenceName = "SRV_SEQ")
    @Column(name = "SRV_ID", nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "SRV_CODE", nullable = false)
    @Size(min = 3, max = 10)
    private String code;

    @Column(name = "SRV_NAME", nullable = false)
    private String name;

    @Column(name = "SRV_DESCRIPTION")
    private String description;

    @Column(name = "SRV_PRICE")
    private Double price;

    @Column(name = "SRV_AVAILABLE", nullable = false)
    private boolean available = true;

    @Column(name = "SRV_DURATION")
    private Integer duration;

    @Column(name = "SRV_REMOVED")
    private boolean removed;

    @ManyToOne
    @JoinColumn(referencedColumnName = "CMP_ID", name = "SRV_CMP_ID", nullable = false)
    private Company company;

}
