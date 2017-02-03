package com.pervazive.kheddah.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.pervazive.kheddah.domain.enumeration.PAStatus;

/**
 * A PASaxCode.
 */
@Entity
@Table(name = "pa_saxcode")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PASaxCode implements Serializable {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "pastatus")
    private PAStatus pastatus;

    @ManyToOne
    private PAOrganization paorgsc;

    @ManyToOne
    private PAProject paprosc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDistalarm() {
        return distalarm;
    }

    public PASaxCode distalarm(String distalarm) {
        this.distalarm = distalarm;
        return this;
    }

    public void setDistalarm(String distalarm) {
        this.distalarm = distalarm;
    }

    public String getSaxcode() {
        return saxcode;
    }

    public PASaxCode saxcode(String saxcode) {
        this.saxcode = saxcode;
        return this;
    }

    public void setSaxcode(String saxcode) {
        this.saxcode = saxcode;
    }

    public String getTotal() {
        return total;
    }

    public PASaxCode total(String total) {
        this.total = total;
        return this;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPainterval() {
        return painterval;
    }

    public PASaxCode painterval(String painterval) {
        this.painterval = painterval;
        return this;
    }

    public void setPainterval(String painterval) {
        this.painterval = painterval;
    }

    public PAStatus getPastatus() {
        return pastatus;
    }

    public PASaxCode pastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
        return this;
    }

    public void setPastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
    }

    public PAOrganization getPaorgsc() {
        return paorgsc;
    }

    public PASaxCode paorgsc(PAOrganization pAOrganization) {
        this.paorgsc = pAOrganization;
        return this;
    }

    public void setPaorgsc(PAOrganization pAOrganization) {
        this.paorgsc = pAOrganization;
    }

    public PAProject getPaprosc() {
        return paprosc;
    }

    public PASaxCode paprosc(PAProject pAProject) {
        this.paprosc = pAProject;
        return this;
    }

    public void setPaprosc(PAProject pAProject) {
        this.paprosc = pAProject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PASaxCode pASaxCode = (PASaxCode) o;
        if (pASaxCode.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pASaxCode.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PASaxCode{" +
            "id=" + id +
            ", distalarm='" + distalarm + "'" +
            ", saxcode='" + saxcode + "'" +
            ", total='" + total + "'" +
            ", painterval='" + painterval + "'" +
            ", pastatus='" + pastatus + "'" +
            '}';
    }
}
