package com.pervazive.kheddah.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.pervazive.kheddah.domain.enumeration.PAStatus;

/**
 * A PASaxCodeTmp.
 */
@Entity
@Table(name = "pa_saxcode_tmp")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PASaxCodeTmp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "distalarm")
    private String distalarm;

    @Column(name = "saxcode")
    private String saxcode;

    @Column(name = "total")
    private String total;

    @Column(name = "painterval")
    private String painterval;

    @Column(name = "severity")
    private String severity;

    @Enumerated(EnumType.STRING)
    @Column(name = "pastatus")
    private PAStatus pastatus;

    @ManyToOne
    private PAOrganization paorgsct;

    @ManyToOne
    private PAProject paprosct;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDistalarm() {
        return distalarm;
    }

    public PASaxCodeTmp distalarm(String distalarm) {
        this.distalarm = distalarm;
        return this;
    }

    public void setDistalarm(String distalarm) {
        this.distalarm = distalarm;
    }

    public String getSaxcode() {
        return saxcode;
    }

    public PASaxCodeTmp saxcode(String saxcode) {
        this.saxcode = saxcode;
        return this;
    }

    public void setSaxcode(String saxcode) {
        this.saxcode = saxcode;
    }

    public String getTotal() {
        return total;
    }

    public PASaxCodeTmp total(String total) {
        this.total = total;
        return this;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPainterval() {
        return painterval;
    }

    public PASaxCodeTmp painterval(String painterval) {
        this.painterval = painterval;
        return this;
    }

    public void setPainterval(String painterval) {
        this.painterval = painterval;
    }

    public String getSeverity() {
        return severity;
    }

    public PASaxCodeTmp severity(String severity) {
        this.severity = severity;
        return this;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public PAStatus getPastatus() {
        return pastatus;
    }

    public PASaxCodeTmp pastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
        return this;
    }

    public void setPastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
    }

    public PAOrganization getPaorgsct() {
        return paorgsct;
    }

    public PASaxCodeTmp paorgsct(PAOrganization pAOrganization) {
        this.paorgsct = pAOrganization;
        return this;
    }

    public void setPaorgsct(PAOrganization pAOrganization) {
        this.paorgsct = pAOrganization;
    }

    public PAProject getPaprosct() {
        return paprosct;
    }

    public PASaxCodeTmp paprosct(PAProject pAProject) {
        this.paprosct = pAProject;
        return this;
    }

    public void setPaprosct(PAProject pAProject) {
        this.paprosct = pAProject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PASaxCodeTmp pASaxCodeTmp = (PASaxCodeTmp) o;
        if (pASaxCodeTmp.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pASaxCodeTmp.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PASaxCodeTmp{" +
            "id=" + id +
            ", distalarm='" + distalarm + "'" +
            ", saxcode='" + saxcode + "'" +
            ", total='" + total + "'" +
            ", painterval='" + painterval + "'" +
            ", severity='" + severity + "'" +
            ", pastatus='" + pastatus + "'" +
            '}';
    }
}
