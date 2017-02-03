package com.pervazive.kheddah.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.pervazive.kheddah.domain.enumeration.PAStatus;

/**
 * A PAAlarmActuality.
 */
@Entity
@Table(name = "pa_alarmactuality")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PAAlarmActuality implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nename")
    private String nename;

    @Column(name = "faulttype")
    private String faulttype;

    @Column(name = "severity")
    private String severity;

    @Column(name = "siteid")
    private String siteid;

    @Column(name = "sitepriority")
    private String sitepriority;

    @Column(name = "predictionmatched")
    private Integer predictionmatched;

    @Column(name = "occured")
    private ZonedDateTime occured;

    @Column(name = "pmtcreated")
    private Integer pmtcreated;

    @Column(name = "totalevents")
    private Integer totalevents;

    @Column(name = "failedcountmatch")
    private Integer failedcountmatch;

    @Column(name = "nofailcountmatch")
    private Integer nofailcountmatch;

    @Enumerated(EnumType.STRING)
    @Column(name = "pastatus")
    private PAStatus pastatus;

    @ManyToOne
    private PAOrganization paorgaa;

    @ManyToOne
    private PAProject paproaa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNename() {
        return nename;
    }

    public PAAlarmActuality nename(String nename) {
        this.nename = nename;
        return this;
    }

    public void setNename(String nename) {
        this.nename = nename;
    }

    public String getFaulttype() {
        return faulttype;
    }

    public PAAlarmActuality faulttype(String faulttype) {
        this.faulttype = faulttype;
        return this;
    }

    public void setFaulttype(String faulttype) {
        this.faulttype = faulttype;
    }

    public String getSeverity() {
        return severity;
    }

    public PAAlarmActuality severity(String severity) {
        this.severity = severity;
        return this;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getSiteid() {
        return siteid;
    }

    public PAAlarmActuality siteid(String siteid) {
        this.siteid = siteid;
        return this;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    public String getSitepriority() {
        return sitepriority;
    }

    public PAAlarmActuality sitepriority(String sitepriority) {
        this.sitepriority = sitepriority;
        return this;
    }

    public void setSitepriority(String sitepriority) {
        this.sitepriority = sitepriority;
    }

    public Integer getPredictionmatched() {
        return predictionmatched;
    }

    public PAAlarmActuality predictionmatched(Integer predictionmatched) {
        this.predictionmatched = predictionmatched;
        return this;
    }

    public void setPredictionmatched(Integer predictionmatched) {
        this.predictionmatched = predictionmatched;
    }

    public ZonedDateTime getOccured() {
        return occured;
    }

    public PAAlarmActuality occured(ZonedDateTime occured) {
        this.occured = occured;
        return this;
    }

    public void setOccured(ZonedDateTime occured) {
        this.occured = occured;
    }

    public Integer getPmtcreated() {
        return pmtcreated;
    }

    public PAAlarmActuality pmtcreated(Integer pmtcreated) {
        this.pmtcreated = pmtcreated;
        return this;
    }

    public void setPmtcreated(Integer pmtcreated) {
        this.pmtcreated = pmtcreated;
    }

    public Integer getTotalevents() {
        return totalevents;
    }

    public PAAlarmActuality totalevents(Integer totalevents) {
        this.totalevents = totalevents;
        return this;
    }

    public void setTotalevents(Integer totalevents) {
        this.totalevents = totalevents;
    }

    public Integer getFailedcountmatch() {
        return failedcountmatch;
    }

    public PAAlarmActuality failedcountmatch(Integer failedcountmatch) {
        this.failedcountmatch = failedcountmatch;
        return this;
    }

    public void setFailedcountmatch(Integer failedcountmatch) {
        this.failedcountmatch = failedcountmatch;
    }

    public Integer getNofailcountmatch() {
        return nofailcountmatch;
    }

    public PAAlarmActuality nofailcountmatch(Integer nofailcountmatch) {
        this.nofailcountmatch = nofailcountmatch;
        return this;
    }

    public void setNofailcountmatch(Integer nofailcountmatch) {
        this.nofailcountmatch = nofailcountmatch;
    }

    public PAStatus getPastatus() {
        return pastatus;
    }

    public PAAlarmActuality pastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
        return this;
    }

    public void setPastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
    }

    public PAOrganization getPaorgaa() {
        return paorgaa;
    }

    public PAAlarmActuality paorgaa(PAOrganization pAOrganization) {
        this.paorgaa = pAOrganization;
        return this;
    }

    public void setPaorgaa(PAOrganization pAOrganization) {
        this.paorgaa = pAOrganization;
    }

    public PAProject getPaproaa() {
        return paproaa;
    }

    public PAAlarmActuality paproaa(PAProject pAProject) {
        this.paproaa = pAProject;
        return this;
    }

    public void setPaproaa(PAProject pAProject) {
        this.paproaa = pAProject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PAAlarmActuality pAAlarmActuality = (PAAlarmActuality) o;
        if (pAAlarmActuality.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pAAlarmActuality.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PAAlarmActuality{" +
            "id=" + id +
            ", nename='" + nename + "'" +
            ", faulttype='" + faulttype + "'" +
            ", severity='" + severity + "'" +
            ", siteid='" + siteid + "'" +
            ", sitepriority='" + sitepriority + "'" +
            ", predictionmatched='" + predictionmatched + "'" +
            ", occured='" + occured + "'" +
            ", pmtcreated='" + pmtcreated + "'" +
            ", totalevents='" + totalevents + "'" +
            ", failedcountmatch='" + failedcountmatch + "'" +
            ", nofailcountmatch='" + nofailcountmatch + "'" +
            ", pastatus='" + pastatus + "'" +
            '}';
    }
}
