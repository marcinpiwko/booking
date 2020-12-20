package com.piwko.booking.persistence.model;

import com.piwko.booking.util.DateTimeUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "RESERVATIONS")
@Table(name = "RESERVATIONS")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Reservation implements IdEntity {

    public interface Status {
        String PENDING = "P";
        String CANCELLED = "C";
        String DONE = "D";
        String ABSENT = "A";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "RESERVATION_ID_GENERATOR")
    @SequenceGenerator(name = "RESERVATION_ID_GENERATOR", sequenceName = "RES_SEQ")
    @Column(name = "RES_ID", nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "RES_DATE", nullable = false)
    private LocalDateTime reservationDate;

    @OneToOne
    @JoinColumn(referencedColumnName = "LOC_ID", name = "RES_LOC_ID", nullable = false)
    private Location location;

    @OneToOne
    @JoinColumn(referencedColumnName = "USR_ID", name = "RES_USR_ID")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "VSR_ID", name = "RES_VSR_ID")
    private VirtualUser virtualUser;

    @OneToOne
    @JoinColumn(referencedColumnName = "SRV_ID", name = "RES_SRV_ID", nullable = false)
    private Service service;

    @OneToOne
    @JoinColumn(referencedColumnName = "EMP_ID", name = "RES_EMP_ID", nullable = false)
    private Employee employee;

    @Column(name = "RES_STATUS", nullable = false)
    private String status;

    @Column(name = "RES_CREATION_DATE", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "RES_MODIFICATION_DATE")
    private LocalDateTime modificationDate;

    @Column(name = "RES_CANCELLATION_DATE")
    private LocalDateTime cancellationDate;

    @Column(name = "RES_CANCELLATION_REASON")
    private String cancellationReason;

    @PrePersist
    public void prePersist() {
        this.creationDate = DateTimeUtil.getCurrentDate();
    }

    @PreUpdate
    public void preUpdate() {
        this.modificationDate = DateTimeUtil.getCurrentDate();
    }
}
