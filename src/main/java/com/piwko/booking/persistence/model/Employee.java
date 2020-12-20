package com.piwko.booking.persistence.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity(name = "EMPLOYEES")
@Table(name = "EMPLOYEES")
@Where(clause = "NOT EMP_REMOVED")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Employee implements CodeNameEntity, SoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "EMPLOYEE_ID_GENERATOR")
    @SequenceGenerator(name = "EMPLOYEE_ID_GENERATOR", sequenceName = "EMP_SEQ")
    @Column(name = "EMP_ID", nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "EMP_FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "EMP_LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "EMP_CODE", nullable = false)
    @Size(min = 3, max = 10)
    private String code;

    @OneToOne
    @JoinColumn(referencedColumnName = "LOC_ID", name = "EMP_LOC_ID", nullable = false)
    private Location location;

    @ManyToMany
    private Set<Service> services;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "WRH_ID", name = "EMP_WRH_ID", nullable = false)
    private WorkingHours workingHours;

    @Column(name = "EMP_REMOVED")
    private boolean removed;

    public String getName() {
        return firstName + " " + lastName;
    }
}
