package com.piwko.booking.persistence.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity(name = "ROLES")
@Table(name = "ROLES")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Role implements IdEntity {

    public enum RoleType {
        USER,
        COMPANY_USER,
        ADMIN,
        VIRTUAL_USER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ROLE_ID_GENERATOR")
    @SequenceGenerator(name = "ROLE_ID_GENERATOR", sequenceName = "ROL_SEQ")
    @Column(name = "ROL_ID")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "ROL_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType type;

    public String getName() {
        return type.name();
    }
}

