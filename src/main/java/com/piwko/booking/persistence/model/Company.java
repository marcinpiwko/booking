package com.piwko.booking.persistence.model;

import com.piwko.booking.util.DateTimeUtil;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "COMPANIES")
@Data
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CMP_ID", nullable = false)
    private Long id;

    @Column(name = "CMP_CODE", nullable = false)
    private String code;

    @Column(name = "CMP_NAME", nullable = false)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Specialization> specializations;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Location> locations;

    @Column(name = "CMP_CREATION_DATE", nullable = false)
    private Date creationDate;

    @Column(name = "CMP_MODIFICATION_DATE")
    private Date modificationDate;

    @PrePersist
    public void prePersist() {
        if (this.creationDate == null) {
            this.creationDate = DateTimeUtil.getCurrentDate();
        } else {
            this.modificationDate = DateTimeUtil.getCurrentDate();
        }
    }

}
