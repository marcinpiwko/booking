package com.piwko.booking.persistence.model;

import com.piwko.booking.util.DateTimeUtil;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "RESERVATIONS")
@Data
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RES_ID", nullable = false)
    private Long id;

    @Column(name = "RES_DATE", nullable = false)
    private Date reservationDate;

    @OneToOne()
    @JoinColumn(referencedColumnName = "LOC_ID", name = "RES_LOC_ID", nullable = false)
    private Location location;

    @OneToOne()
    @JoinColumn(referencedColumnName = "USR_ID", name = "RES_USR_ID", nullable = false)
    private User user;

    @OneToOne()
    @JoinColumn(referencedColumnName = "SRV_ID", name = "RES_SRV_ID", nullable = false)
    private Service service;

    @OneToOne
    @JoinColumn(referencedColumnName = "EMP_ID", name = "RES_EMP_ID", nullable = false)
    private Employee employee;

    @Column(name = "RES_CREATION_DATE", nullable = false)
    private Date creationDate;

    @Column(name = "RES_MODIFICATION_DATE")
    private Date modificationDate;

    @Column(name = "RES_CANCELLATION_DATE")
    private Date cancellationDate;

    @PrePersist
    public void prePersist() {
        if (this.creationDate == null) {
            this.creationDate = DateTimeUtil.getCurrentDate();
        } else {
            this.modificationDate = DateTimeUtil.getCurrentDate();
        }
    }
}
