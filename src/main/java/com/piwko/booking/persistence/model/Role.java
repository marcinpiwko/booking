package com.piwko.booking.persistence.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "ROLES")
@Data
public class Role {

    public enum RoleType {
        USER,
        COMPANY_USER,
        ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROL_ID")
    private Long id;

    @Column(name = "ROL_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType type;

    public String getName() {
        return type.name();
    }
}

