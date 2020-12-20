package com.piwko.booking.persistence.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;

@Entity(name = "VIRTUAL_USERS")
@Table(name = "VIRTUAL_USERS")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class VirtualUser implements IdEntity {

    public interface Gender {
        String MALE = "M";
        String FEMALE = "F";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "V_USER_ID_GENERATOR")
    @SequenceGenerator(name = "V_USER_ID_GENERATOR", sequenceName = "VSR_SEQ")
    @Column(name = "VSR_ID", nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Email
    @Column(name = "VSR_EMAIL", nullable = false)
    private String email;

    @Column(name = "VSR_FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "VSR_LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "VSR_BIRTH_DATE")
    private LocalDate birthDate;

    @Column(name = "VSR_PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "VSR_GENDER", nullable = false)
    private String gender;

}
