package com.pervazive.kheddah.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.pervazive.kheddah.domain.enumeration.PAStatus;

/**
 * A PAAccPrecision.
 */
@Entity
@Table(name = "pa_accprecision")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PAAccPrecision implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "totalpredictions")
    private Integer totalpredictions;

    @Column(name = "failpredictions")
    private Integer failpredictions;

    @Column(name = "nofailpredictions")
    private Integer nofailpredictions;

    @Column(name = "totalevents")
    private Integer totalevents;

    @Column(name = "pfailmatched")
    private Integer pfailmatched;

    @Column(name = "pnofailmatched")
    private Integer pnofailmatched;

    @Column(name = "predictiondate")
    private ZonedDateTime predictiondate;

    @Column(name = "accuracyval")
    private Integer accuracyval;

    @Column(name = "prcisionval")
    private Integer prcisionval;

    @Enumerated(EnumType.STRING)
    @Column(name = "pastatus")
    private PAStatus pastatus;

    @ManyToOne
    private PAProject paproap;

    @ManyToOne
    private PAOrganization paorgap;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTotalpredictions() {
        return totalpredictions;
    }

    public PAAccPrecision totalpredictions(Integer totalpredictions) {
        this.totalpredictions = totalpredictions;
        return this;
    }

    public void setTotalpredictions(Integer totalpredictions) {
        this.totalpredictions = totalpredictions;
    }

    public Integer getFailpredictions() {
        return failpredictions;
    }

    public PAAccPrecision failpredictions(Integer failpredictions) {
        this.failpredictions = failpredictions;
        return this;
    }

    public void setFailpredictions(Integer failpredictions) {
        this.failpredictions = failpredictions;
    }

    public Integer getNofailpredictions() {
        return nofailpredictions;
    }

    public PAAccPrecision nofailpredictions(Integer nofailpredictions) {
        this.nofailpredictions = nofailpredictions;
        return this;
    }

    public void setNofailpredictions(Integer nofailpredictions) {
        this.nofailpredictions = nofailpredictions;
    }

    public Integer getTotalevents() {
        return totalevents;
    }

    public PAAccPrecision totalevents(Integer totalevents) {
        this.totalevents = totalevents;
        return this;
    }

    public void setTotalevents(Integer totalevents) {
        this.totalevents = totalevents;
    }

    public Integer getPfailmatched() {
        return pfailmatched;
    }

    public PAAccPrecision pfailmatched(Integer pfailmatched) {
        this.pfailmatched = pfailmatched;
        return this;
    }

    public void setPfailmatched(Integer pfailmatched) {
        this.pfailmatched = pfailmatched;
    }

    public Integer getPnofailmatched() {
        return pnofailmatched;
    }

    public PAAccPrecision pnofailmatched(Integer pnofailmatched) {
        this.pnofailmatched = pnofailmatched;
        return this;
    }

    public void setPnofailmatched(Integer pnofailmatched) {
        this.pnofailmatched = pnofailmatched;
    }

    public ZonedDateTime getPredictiondate() {
        return predictiondate;
    }

    public PAAccPrecision predictiondate(ZonedDateTime predictiondate) {
        this.predictiondate = predictiondate;
        return this;
    }

    public void setPredictiondate(ZonedDateTime predictiondate) {
        this.predictiondate = predictiondate;
    }

    public Integer getAccuracyval() {
        return accuracyval;
    }

    public PAAccPrecision accuracyval(Integer accuracyval) {
        this.accuracyval = accuracyval;
        return this;
    }

    public void setAccuracyval(Integer accuracyval) {
        this.accuracyval = accuracyval;
    }

    public Integer getPrcisionval() {
        return prcisionval;
    }

    public PAAccPrecision prcisionval(Integer prcisionval) {
        this.prcisionval = prcisionval;
        return this;
    }

    public void setPrcisionval(Integer prcisionval) {
        this.prcisionval = prcisionval;
    }

    public PAStatus getPastatus() {
        return pastatus;
    }

    public PAAccPrecision pastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
        return this;
    }

    public void setPastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
    }

    public PAProject getPaproap() {
        return paproap;
    }

    public PAAccPrecision paproap(PAProject pAProject) {
        this.paproap = pAProject;
        return this;
    }

    public void setPaproap(PAProject pAProject) {
        this.paproap = pAProject;
    }

    public PAOrganization getPaorgap() {
        return paorgap;
    }

    public PAAccPrecision paorgap(PAOrganization pAOrganization) {
        this.paorgap = pAOrganization;
        return this;
    }

    public void setPaorgap(PAOrganization pAOrganization) {
        this.paorgap = pAOrganization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PAAccPrecision pAAccPrecision = (PAAccPrecision) o;
        if (pAAccPrecision.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pAAccPrecision.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PAAccPrecision{" +
            "id=" + id +
            ", totalpredictions='" + totalpredictions + "'" +
            ", failpredictions='" + failpredictions + "'" +
            ", nofailpredictions='" + nofailpredictions + "'" +
            ", totalevents='" + totalevents + "'" +
            ", pfailmatched='" + pfailmatched + "'" +
            ", pnofailmatched='" + pnofailmatched + "'" +
            ", predictiondate='" + predictiondate + "'" +
            ", accuracyval='" + accuracyval + "'" +
            ", prcisionval='" + prcisionval + "'" +
            ", pastatus='" + pastatus + "'" +
            '}';
    }
}
