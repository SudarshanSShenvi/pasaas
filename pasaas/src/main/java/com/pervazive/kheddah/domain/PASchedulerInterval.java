package com.pervazive.kheddah.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.pervazive.kheddah.domain.enumeration.PAStatus;

/**
 * A PASchedulerInterval.
 */
@Entity
@Table(name = "pa_scheduler_interval")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PASchedulerInterval implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "hourval")
    private Integer hourval;

    @Column(name = "minutesval")
    private Integer minutesval;

    @Column(name = "schname")
    private String schname;

    @Column(name = "dcname")
    private String dcname;

    @Enumerated(EnumType.STRING)
    @Column(name = "pastatus")
    private PAStatus pastatus;

    @ManyToOne
    private PAOrganization paorgsci;

    @ManyToOne
    private PAProject paprosci;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public PASchedulerInterval type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getHourval() {
        return hourval;
    }

    public PASchedulerInterval hourval(Integer hourval) {
        this.hourval = hourval;
        return this;
    }

    public void setHourval(Integer hourval) {
        this.hourval = hourval;
    }

    public Integer getMinutesval() {
        return minutesval;
    }

    public PASchedulerInterval minutesval(Integer minutesval) {
        this.minutesval = minutesval;
        return this;
    }

    public void setMinutesval(Integer minutesval) {
        this.minutesval = minutesval;
    }

    public String getSchname() {
        return schname;
    }

    public PASchedulerInterval schname(String schname) {
        this.schname = schname;
        return this;
    }

    public void setSchname(String schname) {
        this.schname = schname;
    }

    public String getDcname() {
        return dcname;
    }

    public PASchedulerInterval dcname(String dcname) {
        this.dcname = dcname;
        return this;
    }

    public void setDcname(String dcname) {
        this.dcname = dcname;
    }

    public PAStatus getPastatus() {
        return pastatus;
    }

    public PASchedulerInterval pastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
        return this;
    }

    public void setPastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
    }

    public PAOrganization getPaorgsci() {
        return paorgsci;
    }

    public PASchedulerInterval paorgsci(PAOrganization pAOrganization) {
        this.paorgsci = pAOrganization;
        return this;
    }

    public void setPaorgsci(PAOrganization pAOrganization) {
        this.paorgsci = pAOrganization;
    }

    public PAProject getPaprosci() {
        return paprosci;
    }

    public PASchedulerInterval paprosci(PAProject pAProject) {
        this.paprosci = pAProject;
        return this;
    }

    public void setPaprosci(PAProject pAProject) {
        this.paprosci = pAProject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PASchedulerInterval pASchedulerInterval = (PASchedulerInterval) o;
        if (pASchedulerInterval.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pASchedulerInterval.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PASchedulerInterval{" +
            "id=" + id +
            ", type='" + type + "'" +
            ", hourval='" + hourval + "'" +
            ", minutesval='" + minutesval + "'" +
            ", schname='" + schname + "'" +
            ", dcname='" + dcname + "'" +
            ", pastatus='" + pastatus + "'" +
            '}';
    }
}
