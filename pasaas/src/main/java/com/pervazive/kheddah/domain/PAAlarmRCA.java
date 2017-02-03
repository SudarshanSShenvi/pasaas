package com.pervazive.kheddah.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.pervazive.kheddah.domain.enumeration.PAStatus;

/**
 * A PAAlarmRCA.
 */
@Entity
@Table(name = "pa_alarm_rca")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PAAlarmRCA implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "alarmno")
    private String alarmno;

    @Column(name = "alarmtext")
    private String alarmtext;

    @Column(name = "alarmtype")
    private String alarmtype;

    @Column(name = "rcdetails")
    private String rcdetails;

    @Enumerated(EnumType.STRING)
    @Column(name = "pastatus")
    private PAStatus pastatus;

    @ManyToOne
    private PAProject paproarc;

    @ManyToOne
    private PAOrganization paorgarc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlarmno() {
        return alarmno;
    }

    public PAAlarmRCA alarmno(String alarmno) {
        this.alarmno = alarmno;
        return this;
    }

    public void setAlarmno(String alarmno) {
        this.alarmno = alarmno;
    }

    public String getAlarmtext() {
        return alarmtext;
    }

    public PAAlarmRCA alarmtext(String alarmtext) {
        this.alarmtext = alarmtext;
        return this;
    }

    public void setAlarmtext(String alarmtext) {
        this.alarmtext = alarmtext;
    }

    public String getAlarmtype() {
        return alarmtype;
    }

    public PAAlarmRCA alarmtype(String alarmtype) {
        this.alarmtype = alarmtype;
        return this;
    }

    public void setAlarmtype(String alarmtype) {
        this.alarmtype = alarmtype;
    }

    public String getRcdetails() {
        return rcdetails;
    }

    public PAAlarmRCA rcdetails(String rcdetails) {
        this.rcdetails = rcdetails;
        return this;
    }

    public void setRcdetails(String rcdetails) {
        this.rcdetails = rcdetails;
    }

    public PAStatus getPastatus() {
        return pastatus;
    }

    public PAAlarmRCA pastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
        return this;
    }

    public void setPastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
    }

    public PAProject getPaproarc() {
        return paproarc;
    }

    public PAAlarmRCA paproarc(PAProject pAProject) {
        this.paproarc = pAProject;
        return this;
    }

    public void setPaproarc(PAProject pAProject) {
        this.paproarc = pAProject;
    }

    public PAOrganization getPaorgarc() {
        return paorgarc;
    }

    public PAAlarmRCA paorgarc(PAOrganization pAOrganization) {
        this.paorgarc = pAOrganization;
        return this;
    }

    public void setPaorgarc(PAOrganization pAOrganization) {
        this.paorgarc = pAOrganization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PAAlarmRCA pAAlarmRCA = (PAAlarmRCA) o;
        if (pAAlarmRCA.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pAAlarmRCA.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PAAlarmRCA{" +
            "id=" + id +
            ", alarmno='" + alarmno + "'" +
            ", alarmtext='" + alarmtext + "'" +
            ", alarmtype='" + alarmtype + "'" +
            ", rcdetails='" + rcdetails + "'" +
            ", pastatus='" + pastatus + "'" +
            '}';
    }
}
