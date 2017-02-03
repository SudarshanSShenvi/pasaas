package com.pervazive.kheddah.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.pervazive.kheddah.domain.enumeration.PAStatus;

/**
 * A PAReliabilityScore.
 */
@Entity
@Table(name = "pa_reliabilityscore")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PAReliabilityScore implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "predictedalarms")
    private Integer predictedalarms;

    @Column(name = "occuredalarms")
    private Integer occuredalarms;

    @Column(name = "matchedalarms")
    private Integer matchedalarms;

    @Column(name = "created")
    private ZonedDateTime created;

    @Column(name = "predictedpercentage")
    private Float predictedpercentage;

    @Enumerated(EnumType.STRING)
    @Column(name = "pastatus")
    private PAStatus pastatus;

    @ManyToOne
    private PAOrganization paorgrs;

    @ManyToOne
    private PAProject paprors;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPredictedalarms() {
        return predictedalarms;
    }

    public PAReliabilityScore predictedalarms(Integer predictedalarms) {
        this.predictedalarms = predictedalarms;
        return this;
    }

    public void setPredictedalarms(Integer predictedalarms) {
        this.predictedalarms = predictedalarms;
    }

    public Integer getOccuredalarms() {
        return occuredalarms;
    }

    public PAReliabilityScore occuredalarms(Integer occuredalarms) {
        this.occuredalarms = occuredalarms;
        return this;
    }

    public void setOccuredalarms(Integer occuredalarms) {
        this.occuredalarms = occuredalarms;
    }

    public Integer getMatchedalarms() {
        return matchedalarms;
    }

    public PAReliabilityScore matchedalarms(Integer matchedalarms) {
        this.matchedalarms = matchedalarms;
        return this;
    }

    public void setMatchedalarms(Integer matchedalarms) {
        this.matchedalarms = matchedalarms;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public PAReliabilityScore created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public Float getPredictedpercentage() {
        return predictedpercentage;
    }

    public PAReliabilityScore predictedpercentage(Float predictedpercentage) {
        this.predictedpercentage = predictedpercentage;
        return this;
    }

    public void setPredictedpercentage(Float predictedpercentage) {
        this.predictedpercentage = predictedpercentage;
    }

    public PAStatus getPastatus() {
        return pastatus;
    }

    public PAReliabilityScore pastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
        return this;
    }

    public void setPastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
    }

    public PAOrganization getPaorgrs() {
        return paorgrs;
    }

    public PAReliabilityScore paorgrs(PAOrganization pAOrganization) {
        this.paorgrs = pAOrganization;
        return this;
    }

    public void setPaorgrs(PAOrganization pAOrganization) {
        this.paorgrs = pAOrganization;
    }

    public PAProject getPaprors() {
        return paprors;
    }

    public PAReliabilityScore paprors(PAProject pAProject) {
        this.paprors = pAProject;
        return this;
    }

    public void setPaprors(PAProject pAProject) {
        this.paprors = pAProject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PAReliabilityScore pAReliabilityScore = (PAReliabilityScore) o;
        if (pAReliabilityScore.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pAReliabilityScore.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PAReliabilityScore{" +
            "id=" + id +
            ", predictedalarms='" + predictedalarms + "'" +
            ", occuredalarms='" + occuredalarms + "'" +
            ", matchedalarms='" + matchedalarms + "'" +
            ", created='" + created + "'" +
            ", predictedpercentage='" + predictedpercentage + "'" +
            ", pastatus='" + pastatus + "'" +
            '}';
    }
}
