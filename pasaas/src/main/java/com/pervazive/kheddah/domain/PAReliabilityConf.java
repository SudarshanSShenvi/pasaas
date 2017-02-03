package com.pervazive.kheddah.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.pervazive.kheddah.domain.enumeration.PAStatus;

/**
 * A PAReliabilityConf.
 */
@Entity
@Table(name = "pa_reliability_conf")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PAReliabilityConf implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "bscore")
    private Float bscore;

    @Column(name = "cscore")
    private Float cscore;

    @Column(name = "dataset")
    private String dataset;

    @Column(name = "category")
    private String category;

    @Enumerated(EnumType.STRING)
    @Column(name = "pastatus")
    private PAStatus pastatus;

    @ManyToOne
    private PAOrganization paorgrc;

    @ManyToOne
    private PAProject paprorc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getBscore() {
        return bscore;
    }

    public PAReliabilityConf bscore(Float bscore) {
        this.bscore = bscore;
        return this;
    }

    public void setBscore(Float bscore) {
        this.bscore = bscore;
    }

    public Float getCscore() {
        return cscore;
    }

    public PAReliabilityConf cscore(Float cscore) {
        this.cscore = cscore;
        return this;
    }

    public void setCscore(Float cscore) {
        this.cscore = cscore;
    }

    public String getDataset() {
        return dataset;
    }

    public PAReliabilityConf dataset(String dataset) {
        this.dataset = dataset;
        return this;
    }

    public void setDataset(String dataset) {
        this.dataset = dataset;
    }

    public String getCategory() {
        return category;
    }

    public PAReliabilityConf category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public PAStatus getPastatus() {
        return pastatus;
    }

    public PAReliabilityConf pastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
        return this;
    }

    public void setPastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
    }

    public PAOrganization getPaorgrc() {
        return paorgrc;
    }

    public PAReliabilityConf paorgrc(PAOrganization pAOrganization) {
        this.paorgrc = pAOrganization;
        return this;
    }

    public void setPaorgrc(PAOrganization pAOrganization) {
        this.paorgrc = pAOrganization;
    }

    public PAProject getPaprorc() {
        return paprorc;
    }

    public PAReliabilityConf paprorc(PAProject pAProject) {
        this.paprorc = pAProject;
        return this;
    }

    public void setPaprorc(PAProject pAProject) {
        this.paprorc = pAProject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PAReliabilityConf pAReliabilityConf = (PAReliabilityConf) o;
        if (pAReliabilityConf.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pAReliabilityConf.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PAReliabilityConf{" +
            "id=" + id +
            ", bscore='" + bscore + "'" +
            ", cscore='" + cscore + "'" +
            ", dataset='" + dataset + "'" +
            ", category='" + category + "'" +
            ", pastatus='" + pastatus + "'" +
            '}';
    }
}
