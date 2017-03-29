package com.pervazive.kheddah.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pervazive.kheddah.domain.enumeration.PAStatus;

/**
 * A PAOrganization.
 */
@Entity
@Table(name = "pa_organization", uniqueConstraints=@UniqueConstraint(columnNames={"organization"}))
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PAOrganization extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotFound(action = NotFoundAction.IGNORE)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "organization")
    @NotNull
    private String organization;

    @Column(name = "industrytype")
    private String industrytype;

    @Column(name = "website")
    private String website;
    
    @Column(name = "validfrom")
    private ZonedDateTime validfrom;

    @Column(name = "validto")
    private ZonedDateTime validto;

    @Enumerated(EnumType.STRING)
    @Column(name = "pastatus")
    private PAStatus pastatus;

    @OneToMany(mappedBy = "paorgpro", fetch = FetchType.EAGER)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PAProject> paproorgs = new HashSet<>();

    @ManyToOne
    @NotNull
    private PABusinessPlan pabporg;

    @OneToMany(mappedBy = "paorgrs")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PAReliabilityScore> parsorgs = new HashSet<>();

    @OneToMany(mappedBy = "paorgrc")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PAReliabilityConf> parcorgs = new HashSet<>();

    @OneToMany(mappedBy = "paorgps")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PAPredictionScore> papsorgs = new HashSet<>();

    @OneToMany(mappedBy = "paorgsct")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PASaxCodeTmp> pasctorgs = new HashSet<>();

    @OneToMany(mappedBy = "paorgsc")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PASaxCode> pascorgs = new HashSet<>();

    @OneToMany(mappedBy = "paorgaa")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PAAlarmActuality> paaaorgs = new HashSet<>();

    @OneToMany(mappedBy = "paorgpre")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PAPrediction> papreorgs = new HashSet<>();

    @OneToMany(mappedBy = "paorgrep")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PAReport> pareporgs = new HashSet<>();

    @OneToMany(mappedBy = "paorgap")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PAAccPrecision> paaporgs = new HashSet<>();

    @OneToMany(mappedBy = "paorgfu")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PAFileUpload> pafuorgs = new HashSet<>();

    @OneToMany(mappedBy = "paorgpmt")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PAPMTRequest> papmtorgs = new HashSet<>();

    @OneToMany(mappedBy = "paorgdc")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PADataConnector> padcorgs = new HashSet<>();

    @OneToMany(mappedBy = "paorgsch")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PAScheduler> paschorgs = new HashSet<>();

    @OneToMany(mappedBy = "paorgsci")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PASchedulerInterval> pasciorgs = new HashSet<>();

    @OneToMany(mappedBy = "paorgarc")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PAAlarmRCA> paarcorgs = new HashSet<>();

    @OneToMany(mappedBy = "paorgned")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PANEDetails> panedorgs = new HashSet<>();
    
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "pa_user_organization",
        joinColumns = {@JoinColumn(name = "organization_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<User> pausers = new HashSet<>();
    
   public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganization() {
        return organization;
    }

    public PAOrganization organization(String organization) {
        this.organization = organization;
        return this;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public ZonedDateTime getValidfrom() {
        return validfrom;
    }

    public PAOrganization validfrom(ZonedDateTime validfrom) {
        this.validfrom = validfrom;
        return this;
    }

    public void setValidfrom(ZonedDateTime validfrom) {
        this.validfrom = validfrom;
    }

    public ZonedDateTime getValidto() {
        return validto;
    }

    public PAOrganization validto(ZonedDateTime validto) {
        this.validto = validto;
        return this;
    }

    public void setValidto(ZonedDateTime validto) {
        this.validto = validto;
    }

    public PAStatus getPastatus() {
        return pastatus;
    }

    public PAOrganization pastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
        return this;
    }

    public void setPastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
    }

    public Set<PAProject> getPaproorgs() {
        return paproorgs;
    }

    public PAOrganization paproorgs(Set<PAProject> pAProjects) {
        this.paproorgs = pAProjects;
        return this;
    }

    public PAOrganization addPaproorg(PAProject pAProject) {
        paproorgs.add(pAProject);
        pAProject.setPaorgpro(this);
        return this;
    }

    public PAOrganization removePaproorg(PAProject pAProject) {
        paproorgs.remove(pAProject);
        pAProject.setPaorgpro(null);
        return this;
    }

    public void setPaproorgs(Set<PAProject> pAProjects) {
        this.paproorgs = pAProjects;
    }

    public PABusinessPlan getPabporg() {
        return pabporg;
    }

    public PAOrganization pabporg(PABusinessPlan pABusinessPlan) {
        this.pabporg = pABusinessPlan;
        return this;
    }

    public void setPabporg(PABusinessPlan pABusinessPlan) {
        this.pabporg = pABusinessPlan;
    }

    public Set<PAReliabilityScore> getParsorgs() {
        return parsorgs;
    }

    public PAOrganization parsorgs(Set<PAReliabilityScore> pAReliabilityScores) {
        this.parsorgs = pAReliabilityScores;
        return this;
    }

    public PAOrganization addParsorg(PAReliabilityScore pAReliabilityScore) {
        parsorgs.add(pAReliabilityScore);
        pAReliabilityScore.setPaorgrs(this);
        return this;
    }

    public PAOrganization removeParsorg(PAReliabilityScore pAReliabilityScore) {
        parsorgs.remove(pAReliabilityScore);
        pAReliabilityScore.setPaorgrs(null);
        return this;
    }

    public void setParsorgs(Set<PAReliabilityScore> pAReliabilityScores) {
        this.parsorgs = pAReliabilityScores;
    }

    public Set<PAReliabilityConf> getParcorgs() {
        return parcorgs;
    }

    public PAOrganization parcorgs(Set<PAReliabilityConf> pAReliabilityConfs) {
        this.parcorgs = pAReliabilityConfs;
        return this;
    }

    public PAOrganization addParcorg(PAReliabilityConf pAReliabilityConf) {
        parcorgs.add(pAReliabilityConf);
        pAReliabilityConf.setPaorgrc(this);
        return this;
    }

    public PAOrganization removeParcorg(PAReliabilityConf pAReliabilityConf) {
        parcorgs.remove(pAReliabilityConf);
        pAReliabilityConf.setPaorgrc(null);
        return this;
    }

    public void setParcorgs(Set<PAReliabilityConf> pAReliabilityConfs) {
        this.parcorgs = pAReliabilityConfs;
    }

    public Set<PAPredictionScore> getPapsorgs() {
        return papsorgs;
    }

    public PAOrganization papsorgs(Set<PAPredictionScore> pAPredictionScores) {
        this.papsorgs = pAPredictionScores;
        return this;
    }

    public PAOrganization addPapsorg(PAPredictionScore pAPredictionScore) {
        papsorgs.add(pAPredictionScore);
        pAPredictionScore.setPaorgps(this);
        return this;
    }

    public PAOrganization removePapsorg(PAPredictionScore pAPredictionScore) {
        papsorgs.remove(pAPredictionScore);
        pAPredictionScore.setPaorgps(null);
        return this;
    }

    public void setPapsorgs(Set<PAPredictionScore> pAPredictionScores) {
        this.papsorgs = pAPredictionScores;
    }

    public Set<PASaxCodeTmp> getPasctorgs() {
        return pasctorgs;
    }

    public PAOrganization pasctorgs(Set<PASaxCodeTmp> pASaxCodeTmps) {
        this.pasctorgs = pASaxCodeTmps;
        return this;
    }

    public PAOrganization addPasctorg(PASaxCodeTmp pASaxCodeTmp) {
        pasctorgs.add(pASaxCodeTmp);
        pASaxCodeTmp.setPaorgsct(this);
        return this;
    }

    public PAOrganization removePasctorg(PASaxCodeTmp pASaxCodeTmp) {
        pasctorgs.remove(pASaxCodeTmp);
        pASaxCodeTmp.setPaorgsct(null);
        return this;
    }

    public void setPasctorgs(Set<PASaxCodeTmp> pASaxCodeTmps) {
        this.pasctorgs = pASaxCodeTmps;
    }

    public Set<PASaxCode> getPascorgs() {
        return pascorgs;
    }

    public PAOrganization pascorgs(Set<PASaxCode> pASaxCodes) {
        this.pascorgs = pASaxCodes;
        return this;
    }

    public PAOrganization addPascorg(PASaxCode pASaxCode) {
        pascorgs.add(pASaxCode);
        pASaxCode.setPaorgsc(this);
        return this;
    }

    public PAOrganization removePascorg(PASaxCode pASaxCode) {
        pascorgs.remove(pASaxCode);
        pASaxCode.setPaorgsc(null);
        return this;
    }

    public void setPascorgs(Set<PASaxCode> pASaxCodes) {
        this.pascorgs = pASaxCodes;
    }

    public Set<PAAlarmActuality> getPaaaorgs() {
        return paaaorgs;
    }

    public PAOrganization paaaorgs(Set<PAAlarmActuality> pAAlarmActualities) {
        this.paaaorgs = pAAlarmActualities;
        return this;
    }

    public PAOrganization addPaaaorg(PAAlarmActuality pAAlarmActuality) {
        paaaorgs.add(pAAlarmActuality);
        pAAlarmActuality.setPaorgaa(this);
        return this;
    }

    public PAOrganization removePaaaorg(PAAlarmActuality pAAlarmActuality) {
        paaaorgs.remove(pAAlarmActuality);
        pAAlarmActuality.setPaorgaa(null);
        return this;
    }

    public void setPaaaorgs(Set<PAAlarmActuality> pAAlarmActualities) {
        this.paaaorgs = pAAlarmActualities;
    }

    public Set<PAPrediction> getPapreorgs() {
        return papreorgs;
    }

    public PAOrganization papreorgs(Set<PAPrediction> pAPredictions) {
        this.papreorgs = pAPredictions;
        return this;
    }

    public PAOrganization addPapreorg(PAPrediction pAPrediction) {
        papreorgs.add(pAPrediction);
        pAPrediction.setPaorgpre(this);
        return this;
    }

    public PAOrganization removePapreorg(PAPrediction pAPrediction) {
        papreorgs.remove(pAPrediction);
        pAPrediction.setPaorgpre(null);
        return this;
    }

    public void setPapreorgs(Set<PAPrediction> pAPredictions) {
        this.papreorgs = pAPredictions;
    }

    public Set<PAReport> getPareporgs() {
        return pareporgs;
    }

    public PAOrganization pareporgs(Set<PAReport> pAReports) {
        this.pareporgs = pAReports;
        return this;
    }

    public PAOrganization addPareporg(PAReport pAReport) {
        pareporgs.add(pAReport);
        pAReport.setPaorgrep(this);
        return this;
    }

    public PAOrganization removePareporg(PAReport pAReport) {
        pareporgs.remove(pAReport);
        pAReport.setPaorgrep(null);
        return this;
    }

    public void setPareporgs(Set<PAReport> pAReports) {
        this.pareporgs = pAReports;
    }

    public Set<PAAccPrecision> getPaaporgs() {
        return paaporgs;
    }

    public PAOrganization paaporgs(Set<PAAccPrecision> pAAccPrecisions) {
        this.paaporgs = pAAccPrecisions;
        return this;
    }

    public PAOrganization addPaaporg(PAAccPrecision pAAccPrecision) {
        paaporgs.add(pAAccPrecision);
        pAAccPrecision.setPaorgap(this);
        return this;
    }

    public PAOrganization removePaaporg(PAAccPrecision pAAccPrecision) {
        paaporgs.remove(pAAccPrecision);
        pAAccPrecision.setPaorgap(null);
        return this;
    }

    public void setPaaporgs(Set<PAAccPrecision> pAAccPrecisions) {
        this.paaporgs = pAAccPrecisions;
    }

    public Set<PAFileUpload> getPafuorgs() {
        return pafuorgs;
    }

    public PAOrganization pafuorgs(Set<PAFileUpload> pAFileUploads) {
        this.pafuorgs = pAFileUploads;
        return this;
    }

    public PAOrganization addPafuorg(PAFileUpload pAFileUpload) {
        pafuorgs.add(pAFileUpload);
        pAFileUpload.setPaorgfu(this);
        return this;
    }

    public PAOrganization removePafuorg(PAFileUpload pAFileUpload) {
        pafuorgs.remove(pAFileUpload);
        pAFileUpload.setPaorgfu(null);
        return this;
    }

    public void setPafuorgs(Set<PAFileUpload> pAFileUploads) {
        this.pafuorgs = pAFileUploads;
    }

    public Set<PAPMTRequest> getPapmtorgs() {
        return papmtorgs;
    }

    public PAOrganization papmtorgs(Set<PAPMTRequest> pAPMTRequests) {
        this.papmtorgs = pAPMTRequests;
        return this;
    }

    public PAOrganization addPapmtorg(PAPMTRequest pAPMTRequest) {
        papmtorgs.add(pAPMTRequest);
        pAPMTRequest.setPaorgpmt(this);
        return this;
    }

    public PAOrganization removePapmtorg(PAPMTRequest pAPMTRequest) {
        papmtorgs.remove(pAPMTRequest);
        pAPMTRequest.setPaorgpmt(null);
        return this;
    }

    public void setPapmtorgs(Set<PAPMTRequest> pAPMTRequests) {
        this.papmtorgs = pAPMTRequests;
    }

    public Set<PADataConnector> getPadcorgs() {
        return padcorgs;
    }

    public PAOrganization padcorgs(Set<PADataConnector> pADataConnectors) {
        this.padcorgs = pADataConnectors;
        return this;
    }

    public PAOrganization addPadcorg(PADataConnector pADataConnector) {
        padcorgs.add(pADataConnector);
        pADataConnector.setPaorgdc(this);
        return this;
    }

    public PAOrganization removePadcorg(PADataConnector pADataConnector) {
        padcorgs.remove(pADataConnector);
        pADataConnector.setPaorgdc(null);
        return this;
    }

    public void setPadcorgs(Set<PADataConnector> pADataConnectors) {
        this.padcorgs = pADataConnectors;
    }

    public Set<PAScheduler> getPaschorgs() {
        return paschorgs;
    }

    public PAOrganization paschorgs(Set<PAScheduler> pASchedulers) {
        this.paschorgs = pASchedulers;
        return this;
    }

    public PAOrganization addPaschorg(PAScheduler pAScheduler) {
        paschorgs.add(pAScheduler);
        pAScheduler.setPaorgsch(this);
        return this;
    }

    public PAOrganization removePaschorg(PAScheduler pAScheduler) {
        paschorgs.remove(pAScheduler);
        pAScheduler.setPaorgsch(null);
        return this;
    }

    public void setPaschorgs(Set<PAScheduler> pASchedulers) {
        this.paschorgs = pASchedulers;
    }

    public Set<PASchedulerInterval> getPasciorgs() {
        return pasciorgs;
    }

    public PAOrganization pasciorgs(Set<PASchedulerInterval> pASchedulerIntervals) {
        this.pasciorgs = pASchedulerIntervals;
        return this;
    }

    public PAOrganization addPasciorg(PASchedulerInterval pASchedulerInterval) {
        pasciorgs.add(pASchedulerInterval);
        pASchedulerInterval.setPaorgsci(this);
        return this;
    }

    public PAOrganization removePasciorg(PASchedulerInterval pASchedulerInterval) {
        pasciorgs.remove(pASchedulerInterval);
        pASchedulerInterval.setPaorgsci(null);
        return this;
    }

    public void setPasciorgs(Set<PASchedulerInterval> pASchedulerIntervals) {
        this.pasciorgs = pASchedulerIntervals;
    }

    public Set<PAAlarmRCA> getPaarcorgs() {
        return paarcorgs;
    }

    public PAOrganization paarcorgs(Set<PAAlarmRCA> pAAlarmRCAS) {
        this.paarcorgs = pAAlarmRCAS;
        return this;
    }

    public PAOrganization addPaarcorg(PAAlarmRCA pAAlarmRCA) {
        paarcorgs.add(pAAlarmRCA);
        pAAlarmRCA.setPaorgarc(this);
        return this;
    }

    public PAOrganization removePaarcorg(PAAlarmRCA pAAlarmRCA) {
        paarcorgs.remove(pAAlarmRCA);
        pAAlarmRCA.setPaorgarc(null);
        return this;
    }

    public void setPaarcorgs(Set<PAAlarmRCA> pAAlarmRCAS) {
        this.paarcorgs = pAAlarmRCAS;
    }

    public Set<PANEDetails> getPanedorgs() {
        return panedorgs;
    }

    public PAOrganization panedorgs(Set<PANEDetails> pANEDetails) {
        this.panedorgs = pANEDetails;
        return this;
    }

    public PAOrganization addPanedorg(PANEDetails pANEDetails) {
        panedorgs.add(pANEDetails);
        pANEDetails.setPaorgned(this);
        return this;
    }

    public PAOrganization removePanedorg(PANEDetails pANEDetails) {
        panedorgs.remove(pANEDetails);
        pANEDetails.setPaorgned(null);
        return this;
    }

    public void setPanedorgs(Set<PANEDetails> pANEDetails) {
        this.panedorgs = pANEDetails;
    }
    
    public Set<User> getPAUsers() {
        return pausers;
    }

    public void setPAUsers(Set<User> pausers) {
        this.pausers = pausers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PAOrganization pAOrganization = (PAOrganization) o;
        if (pAOrganization.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pAOrganization.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PAOrganization{" +
            "id=" + id +
            ", organization='" + organization + "'" +
            ", validfrom='" + validfrom + "'" +
            ", validto='" + validto + "'" +
            ", pastatus='" + pastatus + "'" +
            '}';
    }

	public String getIndustrytype() {
		return industrytype;
	}

	public void setIndustrytype(String industrytype) {
		this.industrytype = industrytype;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
}
