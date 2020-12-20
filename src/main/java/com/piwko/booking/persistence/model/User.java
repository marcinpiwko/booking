package com.piwko.booking.persistence.model;

import com.piwko.booking.util.DateTimeUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity(name = "USERS")
@Table(name = "USERS")
@Where(clause = "NOT USR_REMOVED")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User implements IdEntity, SoftDeleteEntity, UserDetails {

    public interface Gender {
        String MALE = "M";
        String FEMALE = "F";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "USER_ID_GENERATOR")
    @SequenceGenerator(name = "USER_ID_GENERATOR", sequenceName = "USR_SEQ")
    @Column(name = "USR_ID", nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Email
    @Column(name = "USR_EMAIL", nullable = false)
    private String email;

    @Column(name = "USR_PASSWORD", nullable = false)
    private String password;

    @Column(name = "USR_FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "USR_LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "USR_BIRTH_DATE")
    private LocalDate birthDate;

    @Column(name = "USR_PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "USR_GENDER", nullable = false)
    private String gender;

    @Column(name = "USR_REGISTRATION_DATE", nullable = false)
    private LocalDateTime registrationDate;

    @Column(name = "USR_MODIFICATION_DATE")
    private LocalDateTime modificationDate;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(referencedColumnName = "CMP_ID", name = "USR_CMP_ID")
    private Company company;

    @OneToOne
    @JoinColumn(referencedColumnName = "ROL_ID", name = "USR_ROL_ID", nullable = false)
    private Role role;

    @Column(name = "USR_ENABLED")
    private boolean enabled = true;

    @Column(name = "USR_REMOVED")
    private boolean removed;

    @PrePersist
    public void prePersist() {
        this.registrationDate = DateTimeUtil.getCurrentDate();
    }

    @PreUpdate
    public void preUpdate() {
        this.modificationDate = DateTimeUtil.getCurrentDate();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.getType().name()));
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public boolean isCompanyUser() {
        return company != null;
    }
}
