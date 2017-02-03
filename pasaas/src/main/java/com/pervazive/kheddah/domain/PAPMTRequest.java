package com.pervazive.kheddah.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.pervazive.kheddah.domain.enumeration.PMTCategory;

import com.pervazive.kheddah.domain.enumeration.PMTStatus;

import com.pervazive.kheddah.domain.enumeration.PMTPriority;

/**
 * A PAPMTRequest.
 */
@Entity
@Table(name = "pa_pmt_request")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PAPMTRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private PMTCategory category;

    @Column(name = "pmtdescription")
    private String pmtdescription;

    @Column(name = "pmtreason")
    private String pmtreason;

    @Column(name = "pmttitle")
    private String pmttitle;

    @Column(name = "pmttype")
    private String pmttype;

    @Column(name = "siteid")
    private String siteid;

    @Column(name = "sitename")
    private String sitename;

    @Column(name = "sitepriority")
    private String sitepriority;

    @Enumerated(EnumType.STRING)
    @Column(name = "pmtstatus")
    private PMTStatus pmtstatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "pmtpriority")
    private PMTPriority pmtpriority;

    @ManyToOne
    private PAOrganization paorgpmt;

    @ManyToOne
    private PAProject papropmt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PMTCategory getCategory() {
        return category;
    }

    public PAPMTRequest category(PMTCategory category) {
        this.category = category;
        return this;
    }

    public void setCategory(PMTCategory category) {
        this.category = category;
    }

    public String getPmtdescription() {
        return pmtdescription;
    }

    public PAPMTRequest pmtdescription(String pmtdescription) {
        this.pmtdescription = pmtdescription;
        return this;
    }

    public void setPmtdescription(String pmtdescription) {
        this.pmtdescription = pmtdescription;
    }

    public String getPmtreason() {
        return pmtreason;
    }

    public PAPMTRequest pmtreason(String pmtreason) {
        this.pmtreason = pmtreason;
        return this;
    }

    public void setPmtreason(String pmtreason) {
        this.pmtreason = pmtreason;
    }

    public String getPmttitle() {
        return pmttitle;
    }

    public PAPMTRequest pmttitle(String pmttitle) {
        this.pmttitle = pmttitle;
        return this;
    }

    public void setPmttitle(String pmttitle) {
        this.pmttitle = pmttitle;
    }

    public String getPmttype() {
        return pmttype;
    }

    public PAPMTRequest pmttype(String pmttype) {
        this.pmttype = pmttype;
        return this;
    }

    public void setPmttype(String pmttype) {
        this.pmttype = pmttype;
    }

    public String getSiteid() {
        return siteid;
    }

    public PAPMTRequest siteid(String siteid) {
        this.siteid = siteid;
        return this;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    public String getSitename() {
        return sitename;
    }

    public PAPMTRequest sitename(String sitename) {
        this.sitename = sitename;
        return this;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }

    public String getSitepriority() {
        return sitepriority;
    }

    public PAPMTRequest sitepriority(String sitepriority) {
        this.sitepriority = sitepriority;
        return this;
    }

    public void setSitepriority(String sitepriority) {
        this.sitepriority = sitepriority;
    }

    public PMTStatus getPmtstatus() {
        return pmtstatus;
    }

    public PAPMTRequest pmtstatus(PMTStatus pmtstatus) {
        this.pmtstatus = pmtstatus;
        return this;
    }

    public void setPmtstatus(PMTStatus pmtstatus) {
        this.pmtstatus = pmtstatus;
    }

    public PMTPriority getPmtpriority() {
        return pmtpriority;
    }

    public PAPMTRequest pmtpriority(PMTPriority pmtpriority) {
        this.pmtpriority = pmtpriority;
        return this;
    }

    public void setPmtpriority(PMTPriority pmtpriority) {
        this.pmtpriority = pmtpriority;
    }

    public PAOrganization getPaorgpmt() {
        return paorgpmt;
    }

    public PAPMTRequest paorgpmt(PAOrganization pAOrganization) {
        this.paorgpmt = pAOrganization;
        return this;
    }

    public void setPaorgpmt(PAOrganization pAOrganization) {
        this.paorgpmt = pAOrganization;
    }

    public PAProject getPapropmt() {
        return papropmt;
    }

    public PAPMTRequest papropmt(PAProject pAProject) {
        this.papropmt = pAProject;
        return this;
    }

    public void setPapropmt(PAProject pAProject) {
        this.papropmt = pAProject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PAPMTRequest pAPMTRequest = (PAPMTRequest) o;
        if (pAPMTRequest.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pAPMTRequest.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PAPMTRequest{" +
            "id=" + id +
            ", category='" + category + "'" +
            ", pmtdescription='" + pmtdescription + "'" +
            ", pmtreason='" + pmtreason + "'" +
            ", pmttitle='" + pmttitle + "'" +
            ", pmttype='" + pmttype + "'" +
            ", siteid='" + siteid + "'" +
            ", sitename='" + sitename + "'" +
            ", sitepriority='" + sitepriority + "'" +
            ", pmtstatus='" + pmtstatus + "'" +
            ", pmtpriority='" + pmtpriority + "'" +
            '}';
    }
}
