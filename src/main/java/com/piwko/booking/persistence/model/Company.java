package com.piwko.booking.persistence.model;

import com.piwko.booking.util.DateTimeUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "COMPANIES")
@Table(name = "COMPANIES")
@Where(clause = "NOT CMP_REMOVED")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Company implements CodeNameEntity, SoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "COMPANY_ID_GENERATOR")
    @SequenceGenerator(name = "COMPANY_ID_GENERATOR", sequenceName = "CMP_SEQ")
    @Column(name = "CMP_ID", nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "CMP_CODE", nullable = false)
    @Size(min = 3, max = 10)
    private String code;

    @Column(name = "CMP_NAME", nullable = false)
    private String name;

    @ManyToMany
    private Set<Specialization> specializations;

    @OneToMany(mappedBy = "company")
    private Set<Location> locations;

    @OneToMany(mappedBy = "company")
    private Set<Service> services;

    @Column(name = "CMP_CANCELLATION_TIME", nullable = false)
    private Integer cancellationTime;

    @Column(name = "CMP_REGISTRATION_DATE", nullable = false)
    private LocalDateTime registrationDate;

    @Column(name = "CMP_MODIFICATION_DATE")
    private LocalDateTime modificationDate;

    @Column(name = "CMP_REMOVED", nullable = false)
    private boolean removed;

    @PrePersist
    public void prePersist() {
        this.registrationDate = DateTimeUtil.getCurrentDate();
    }

    @PreUpdate
    public void preUpdate() {
        this.modificationDate = DateTimeUtil.getCurrentDate();
    }
}
