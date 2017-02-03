package com.pervazive.kheddah.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.pervazive.kheddah.domain.enumeration.PAStatus;

/**
 * A PAPrediction.
 */
@Entity
@Table(name = "pa_prediction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PAPrediction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "predictiondate")
    private ZonedDateTime predictiondate;

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

    @Column(name = "totalprediction")
    private String totalprediction;

    @Column(name = "failcount")
    private Integer failcount;

    @Column(name = "nofailcount")
    private Integer nofailcount;

    @Column(name = "failprob")
    private Float failprob;

    @Enumerated(EnumType.STRING)
    @Column(name = "pastatus")
    private PAStatus pastatus;

    @ManyToOne
    private PAOrganization paorgpre;

    @ManyToOne
    private PAProject papropre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getPredictiondate() {
        return predictiondate;
    }

    public PAPrediction predictiondate(ZonedDateTime predictiondate) {
        this.predictiondate = predictiondate;
        return this;
    }

    public void setPredictiondate(ZonedDateTime predictiondate) {
        this.predictiondate = predictiondate;
    }

    public String getNename() {
        return nename;
    }

    public PAPrediction nename(String nename) {
        this.nename = nename;
        return this;
    }

    public void setNename(String nename) {
        this.nename = nename;
    }

    public String getFaulttype() {
        return faulttype;
    }

    public PAPrediction faulttype(String faulttype) {
        this.faulttype = faulttype;
        return this;
    }

    public void setFaulttype(String faulttype) {
        this.faulttype = faulttype;
    }

    public String getSeverity() {
        return severity;
    }

    public PAPrediction severity(String severity) {
        this.severity = severity;
        return this;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getSiteid() {
        return siteid;
    }

    public PAPrediction siteid(String siteid) {
        this.siteid = siteid;
        return this;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    public String getSitepriority() {
        return sitepriority;
    }

    public PAPrediction sitepriority(String sitepriority) {
        this.sitepriority = sitepriority;
        return this;
    }

    public void setSitepriority(String sitepriority) {
        this.sitepriority = sitepriority;
    }

    public String getTotalprediction() {
        return totalprediction;
    }

    public PAPrediction totalprediction(String totalprediction) {
        this.totalprediction = totalprediction;
        return this;
    }

    public void setTotalprediction(String totalprediction) {
        this.totalprediction = totalprediction;
    }

    public Integer getFailcount() {
        return failcount;
    }

    public PAPrediction failcount(Integer failcount) {
        this.failcount = failcount;
        return this;
    }

    public void setFailcount(Integer failcount) {
        this.failcount = failcount;
    }

    public Integer getNofailcount() {
        return nofailcount;
    }

    public PAPrediction nofailcount(Integer nofailcount) {
        this.nofailcount = nofailcount;
        return this;
    }

    public void setNofailcount(Integer nofailcount) {
        this.nofailcount = nofailcount;
    }

    public Float getFailprob() {
        return failprob;
    }

    public PAPrediction failprob(Float failprob) {
        this.failprob = failprob;
        return this;
    }

    public void setFailprob(Float failprob) {
        this.failprob = failprob;
    }

    public PAStatus getPastatus() {
        return pastatus;
    }

    public PAPrediction pastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
        return this;
    }

    public void setPastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
    }

    public PAOrganization getPaorgpre() {
        return paorgpre;
    }

    public PAPrediction paorgpre(PAOrganization pAOrganization) {
        this.paorgpre = pAOrganization;
        return this;
    }

    public void setPaorgpre(PAOrganization pAOrganization) {
        this.paorgpre = pAOrganization;
    }

    public PAProject getPapropre() {
        return papropre;
    }

    public PAPrediction papropre(PAProject pAProject) {
        this.papropre = pAProject;
        return this;
    }

    public void setPapropre(PAProject pAProject) {
        this.papropre = pAProject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PAPrediction pAPrediction = (PAPrediction) o;
        if (pAPrediction.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pAPrediction.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PAPrediction{" +
            "id=" + id +
            ", predictiondate='" + predictiondate + "'" +
            ", nename='" + nename + "'" +
            ", faulttype='" + faulttype + "'" +
            ", severity='" + severity + "'" +
            ", siteid='" + siteid + "'" +
            ", sitepriority='" + sitepriority + "'" +
            ", totalprediction='" + totalprediction + "'" +
            ", failcount='" + failcount + "'" +
            ", nofailcount='" + nofailcount + "'" +
            ", failprob='" + failprob + "'" +
            ", pastatus='" + pastatus + "'" +
            '}';
    }
}
