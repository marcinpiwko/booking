package com.piwko.booking.persistence.model;

import com.piwko.booking.util.DateTimeUtil;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "USERS")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USR_ID", nullable = false)
    private Long id;

    @Column(name = "USR_EMAIL", nullable = false)
    private String email;

    @Column(name = "USR_PASSWORD", nullable = false)
    private String password;

    @Column(name = "USR_FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "USR_LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "USR_BIRTH_DATE")
    private Date birthDate;

    @Column(name = "USR_REGISTRATION_DATE", nullable = false)
    private Date registrationDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "CMP_ID", name = "USR_CMP_ID", nullable = false)
    private Company company;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "ROL_ID", name = "USR_ROL_ID", nullable = false)
    private Role role;

    @PrePersist
    public void prePersist() {
        if (this.registrationDate == null) {
            this.registrationDate = DateTimeUtil.getCurrentDate();
        }
    }

    public Boolean isCompanyUser() {
        return company != null;
    }
}
