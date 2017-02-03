package com.pervazive.kheddah.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.pervazive.kheddah.domain.enumeration.PAStatus;

/**
 * A PANEDetails.
 */
@Entity
@Table(name = "pa_ne_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PANEDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "distinguishedname")
    private String distinguishedname;

    @Column(name = "siteid")
    private String siteid;

    @Column(name = "sitename")
    private String sitename;

    @Column(name = "sitelocation")
    private String sitelocation;

    @Column(name = "sitezone")
    private String sitezone;

    @Enumerated(EnumType.STRING)
    @Column(name = "pastatus")
    private PAStatus pastatus;

    @ManyToOne
    private PAOrganization paorgned;

    @ManyToOne
    private PAProject paproned;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDistinguishedname() {
        return distinguishedname;
    }

    public PANEDetails distinguishedname(String distinguishedname) {
        this.distinguishedname = distinguishedname;
        return this;
    }

    public void setDistinguishedname(String distinguishedname) {
        this.distinguishedname = distinguishedname;
    }

    public String getSiteid() {
        return siteid;
    }

    public PANEDetails siteid(String siteid) {
        this.siteid = siteid;
        return this;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    public String getSitename() {
        return sitename;
    }

    public PANEDetails sitename(String sitename) {
        this.sitename = sitename;
        return this;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    public String getSitelocation() {
        return sitelocation;
    }

    public PANEDetails sitelocation(String sitelocation) {
        this.sitelocation = sitelocation;
        return this;
    }

    public void setSitelocation(String sitelocation) {
        this.sitelocation = sitelocation;
    }

    public String getSitezone() {
        return sitezone;
    }

    public PANEDetails sitezone(String sitezone) {
        this.sitezone = sitezone;
        return this;
    }

    public void setSitezone(String sitezone) {
        this.sitezone = sitezone;
    }

    public PAStatus getPastatus() {
        return pastatus;
    }

    public PANEDetails pastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
        return this;
    }

    public void setPastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
    }

    public PAOrganization getPaorgned() {
        return paorgned;
    }

    public PANEDetails paorgned(PAOrganization pAOrganization) {
        this.paorgned = pAOrganization;
        return this;
    }

    public void setPaorgned(PAOrganization pAOrganization) {
        this.paorgned = pAOrganization;
    }

    public PAProject getPaproned() {
        return paproned;
    }

    public PANEDetails paproned(PAProject pAProject) {
        this.paproned = pAProject;
        return this;
    }

    public void setPaproned(PAProject pAProject) {
        this.paproned = pAProject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PANEDetails pANEDetails = (PANEDetails) o;
        if (pANEDetails.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pANEDetails.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PANEDetails{" +
            "id=" + id +
            ", distinguishedname='" + distinguishedname + "'" +
            ", siteid='" + siteid + "'" +
            ", sitename='" + sitename + "'" +
            ", sitelocation='" + sitelocation + "'" +
            ", sitezone='" + sitezone + "'" +
            ", pastatus='" + pastatus + "'" +
            '}';
    }
}
