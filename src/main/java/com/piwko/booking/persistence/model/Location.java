package com.piwko.booking.persistence.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity(name = "LOCATIONS")
@Table(name = "LOCATIONS")
@Where(clause = "NOT LOC_REMOVED")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Location implements CodeNameEntity, SoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "LOCATION_ID_GENERATOR")
    @SequenceGenerator(name = "LOCATION_ID_GENERATOR", sequenceName = "LOC_SEQ")
    @Column(name = "LOC_ID", nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "LOC_CODE", nullable = false)
    @Size(min = 3, max = 10)
    private String code;

    @Column(name = "LOC_NAME", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(referencedColumnName = "CMP_ID", name = "LOC_CMP_ID", nullable = false)
    private Company company;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "ADR_ID", name = "LOC_ADR_ID", nullable = false)
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "WRH_ID", name = "LOC_WRH_ID", nullable = false)
    private WorkingHours workingHours;

    @Column(name = "LOC_REMOVED")
    private boolean removed;

}
