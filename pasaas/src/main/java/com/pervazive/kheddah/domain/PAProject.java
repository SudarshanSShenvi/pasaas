package com.pervazive.kheddah.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PAProject.
 */
@Entity
@Table(name = "pa_project")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PAProject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "projectname")
    private String projectname;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private PAOrganization paorgpro;
    
    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "pa_user_project",
        joinColumns = {@JoinColumn(name = "project_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<User> pausers = new HashSet<>();


	@OneToMany(mappedBy = "paproem")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PAErrorMessage> paempros = new HashSet<>();

    @OneToMany(mappedBy = "papronot")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PANotification> panotpros = new HashSet<>();

    @OneToMany(mappedBy = "paprofu")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PAFileUpload> pafupros = new HashSet<>();

    @OneToMany(mappedBy = "paproap")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PAAccPrecision> paappros = new HashSet<>();

    @OneToMany(mappedBy = "papropre")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PAPrediction> paprepros = new HashSet<>();

    @OneToMany(mappedBy = "paproaa")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PAAlarmActuality> paaapros = new HashSet<>();

    @OneToMany(mappedBy = "paprosc")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PASaxCode> pascpros = new HashSet<>();

    @OneToMany(mappedBy = "paprosct")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PASaxCodeTmp> pasctpros = new HashSet<>();

    @OneToMany(mappedBy = "paprops")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PAPredictionScore> papspros = new HashSet<>();

    @OneToMany(mappedBy = "paprorc")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PAReliabilityConf> parcpros = new HashSet<>();

    @OneToMany(mappedBy = "paprors")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PAReliabilityScore> parspros = new HashSet<>();

    @OneToMany(mappedBy = "papropmt")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PAPMTRequest> papmtpros = new HashSet<>();

    @OneToMany(mappedBy = "paprosci")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PASchedulerInterval> pascipros = new HashSet<>();

    @OneToMany(mappedBy = "paproarc")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PAAlarmRCA> paarcpros = new HashSet<>();

    @OneToMany(mappedBy = "paproned")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PANEDetails> panedpros = new HashSet<>();

    @OneToMany(mappedBy = "paprodc")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PADataConnector> padcpros = new HashSet<>();

    @OneToMany(mappedBy = "paprosch")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PAScheduler> paschpros = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectname() {
        return projectname;
    }

    public PAProject projectname(String projectname) {
        this.projectname = projectname;
        return this;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getDescription() {
        return description;
    }

    public PAProject description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PAOrganization getPaorgpro() {
        return paorgpro;
    }

    public PAProject paorgpro(PAOrganization pAOrganization) {
        this.paorgpro = pAOrganization;
        return this;
    }

    public void setPaorgpro(PAOrganization pAOrganization) {
        this.paorgpro = pAOrganization;
    }

    public Set<PAErrorMessage> getPaempros() {
        return paempros;
    }

    public PAProject paempros(Set<PAErrorMessage> pAErrorMessages) {
        this.paempros = pAErrorMessages;
        return this;
    }

    public PAProject addPaempro(PAErrorMessage pAErrorMessage) {
        paempros.add(pAErrorMessage);
        pAErrorMessage.setPaproem(this);
        return this;
    }

    public PAProject removePaempro(PAErrorMessage pAErrorMessage) {
        paempros.remove(pAErrorMessage);
        pAErrorMessage.setPaproem(null);
        return this;
    }

    public void setPaempros(Set<PAErrorMessage> pAErrorMessages) {
        this.paempros = pAErrorMessages;
    }

    public Set<PANotification> getPanotpros() {
        return panotpros;
    }

    public PAProject panotpros(Set<PANotification> pANotifications) {
        this.panotpros = pANotifications;
        return this;
    }

    public PAProject addPanotpro(PANotification pANotification) {
        panotpros.add(pANotification);
        pANotification.setPapronot(this);
        return this;
    }

    public PAProject removePanotpro(PANotification pANotification) {
        panotpros.remove(pANotification);
        pANotification.setPapronot(null);
        return this;
    }

    public void setPanotpros(Set<PANotification> pANotifications) {
        this.panotpros = pANotifications;
    }

    public Set<PAFileUpload> getPafupros() {
        return pafupros;
    }

    public PAProject pafupros(Set<PAFileUpload> pAFileUploads) {
        this.pafupros = pAFileUploads;
        return this;
    }

    public PAProject addPafupro(PAFileUpload pAFileUpload) {
        pafupros.add(pAFileUpload);
        pAFileUpload.setPaprofu(this);
        return this;
    }

    public PAProject removePafupro(PAFileUpload pAFileUpload) {
        pafupros.remove(pAFileUpload);
        pAFileUpload.setPaprofu(null);
        return this;
    }

    public void setPafupros(Set<PAFileUpload> pAFileUploads) {
        this.pafupros = pAFileUploads;
    }

    public Set<PAAccPrecision> getPaappros() {
        return paappros;
    }

    public PAProject paappros(Set<PAAccPrecision> pAAccPrecisions) {
        this.paappros = pAAccPrecisions;
        return this;
    }

    public PAProject addPaappro(PAAccPrecision pAAccPrecision) {
        paappros.add(pAAccPrecision);
        pAAccPrecision.setPaproap(this);
        return this;
    }

    public PAProject removePaappro(PAAccPrecision pAAccPrecision) {
        paappros.remove(pAAccPrecision);
        pAAccPrecision.setPaproap(null);
        return this;
    }

    public void setPaappros(Set<PAAccPrecision> pAAccPrecisions) {
        this.paappros = pAAccPrecisions;
    }

    public Set<PAPrediction> getPaprepros() {
        return paprepros;
    }

    public PAProject paprepros(Set<PAPrediction> pAPredictions) {
        this.paprepros = pAPredictions;
        return this;
    }

    public PAProject addPaprepro(PAPrediction pAPrediction) {
        paprepros.add(pAPrediction);
        pAPrediction.setPapropre(this);
        return this;
    }

    public PAProject removePaprepro(PAPrediction pAPrediction) {
        paprepros.remove(pAPrediction);
        pAPrediction.setPapropre(null);
        return this;
    }

    public void setPaprepros(Set<PAPrediction> pAPredictions) {
        this.paprepros = pAPredictions;
    }

    public Set<PAAlarmActuality> getPaaapros() {
        return paaapros;
    }

    public PAProject paaapros(Set<PAAlarmActuality> pAAlarmActualities) {
        this.paaapros = pAAlarmActualities;
        return this;
    }

    public PAProject addPaaapro(PAAlarmActuality pAAlarmActuality) {
        paaapros.add(pAAlarmActuality);
        pAAlarmActuality.setPaproaa(this);
        return this;
    }

    public PAProject removePaaapro(PAAlarmActuality pAAlarmActuality) {
        paaapros.remove(pAAlarmActuality);
        pAAlarmActuality.setPaproaa(null);
        return this;
    }

    public void setPaaapros(Set<PAAlarmActuality> pAAlarmActualities) {
        this.paaapros = pAAlarmActualities;
    }

    public Set<PASaxCode> getPascpros() {
        return pascpros;
    }

    public PAProject pascpros(Set<PASaxCode> pASaxCodes) {
        this.pascpros = pASaxCodes;
        return this;
    }

    public PAProject addPascpro(PASaxCode pASaxCode) {
        pascpros.add(pASaxCode);
        pASaxCode.setPaprosc(this);
        return this;
    }

    public PAProject removePascpro(PASaxCode pASaxCode) {
        pascpros.remove(pASaxCode);
        pASaxCode.setPaprosc(null);
        return this;
    }

    public void setPascpros(Set<PASaxCode> pASaxCodes) {
        this.pascpros = pASaxCodes;
    }

    public Set<PASaxCodeTmp> getPasctpros() {
        return pasctpros;
    }

    public PAProject pasctpros(Set<PASaxCodeTmp> pASaxCodeTmps) {
        this.pasctpros = pASaxCodeTmps;
        return this;
    }

    public PAProject addPasctpro(PASaxCodeTmp pASaxCodeTmp) {
        pasctpros.add(pASaxCodeTmp);
        pASaxCodeTmp.setPaprosct(this);
        return this;
    }

    public PAProject removePasctpro(PASaxCodeTmp pASaxCodeTmp) {
        pasctpros.remove(pASaxCodeTmp);
        pASaxCodeTmp.setPaprosct(null);
        return this;
    }

    public void setPasctpros(Set<PASaxCodeTmp> pASaxCodeTmps) {
        this.pasctpros = pASaxCodeTmps;
    }

    public Set<PAPredictionScore> getPapspros() {
        return papspros;
    }

    public PAProject papspros(Set<PAPredictionScore> pAPredictionScores) {
        this.papspros = pAPredictionScores;
        return this;
    }

    public PAProject addPapspro(PAPredictionScore pAPredictionScore) {
        papspros.add(pAPredictionScore);
        pAPredictionScore.setPaprops(this);
        return this;
    }

    public PAProject removePapspro(PAPredictionScore pAPredictionScore) {
        papspros.remove(pAPredictionScore);
        pAPredictionScore.setPaprops(null);
        return this;
    }

    public void setPapspros(Set<PAPredictionScore> pAPredictionScores) {
        this.papspros = pAPredictionScores;
    }

    public Set<PAReliabilityConf> getParcpros() {
        return parcpros;
    }

    public PAProject parcpros(Set<PAReliabilityConf> pAReliabilityConfs) {
        this.parcpros = pAReliabilityConfs;
        return this;
    }

    public PAProject addParcpro(PAReliabilityConf pAReliabilityConf) {
        parcpros.add(pAReliabilityConf);
        pAReliabilityConf.setPaprorc(this);
        return this;
    }

    public PAProject removeParcpro(PAReliabilityConf pAReliabilityConf) {
        parcpros.remove(pAReliabilityConf);
        pAReliabilityConf.setPaprorc(null);
        return this;
    }

    public void setParcpros(Set<PAReliabilityConf> pAReliabilityConfs) {
        this.parcpros = pAReliabilityConfs;
    }

    public Set<PAReliabilityScore> getParspros() {
        return parspros;
    }

    public PAProject parspros(Set<PAReliabilityScore> pAReliabilityScores) {
        this.parspros = pAReliabilityScores;
        return this;
    }

    public PAProject addParspro(PAReliabilityScore pAReliabilityScore) {
        parspros.add(pAReliabilityScore);
        pAReliabilityScore.setPaprors(this);
        return this;
    }

    public PAProject removeParspro(PAReliabilityScore pAReliabilityScore) {
        parspros.remove(pAReliabilityScore);
        pAReliabilityScore.setPaprors(null);
        return this;
    }

    public void setParspros(Set<PAReliabilityScore> pAReliabilityScores) {
        this.parspros = pAReliabilityScores;
    }

    public Set<PAPMTRequest> getPapmtpros() {
        return papmtpros;
    }

    public PAProject papmtpros(Set<PAPMTRequest> pAPMTRequests) {
        this.papmtpros = pAPMTRequests;
        return this;
    }

    public PAProject addPapmtpro(PAPMTRequest pAPMTRequest) {
        papmtpros.add(pAPMTRequest);
        pAPMTRequest.setPapropmt(this);
        return this;
    }

    public PAProject removePapmtpro(PAPMTRequest pAPMTRequest) {
        papmtpros.remove(pAPMTRequest);
        pAPMTRequest.setPapropmt(null);
        return this;
    }

    public void setPapmtpros(Set<PAPMTRequest> pAPMTRequests) {
        this.papmtpros = pAPMTRequests;
    }

    public Set<PASchedulerInterval> getPascipros() {
        return pascipros;
    }

    public PAProject pascipros(Set<PASchedulerInterval> pASchedulerIntervals) {
        this.pascipros = pASchedulerIntervals;
        return this;
    }

    public PAProject addPascipro(PASchedulerInterval pASchedulerInterval) {
        pascipros.add(pASchedulerInterval);
        pASchedulerInterval.setPaprosci(this);
        return this;
    }

    public PAProject removePascipro(PASchedulerInterval pASchedulerInterval) {
        pascipros.remove(pASchedulerInterval);
        pASchedulerInterval.setPaprosci(null);
        return this;
    }

    public void setPascipros(Set<PASchedulerInterval> pASchedulerIntervals) {
        this.pascipros = pASchedulerIntervals;
    }

    public Set<PAAlarmRCA> getPaarcpros() {
        return paarcpros;
    }

    public PAProject paarcpros(Set<PAAlarmRCA> pAAlarmRCAS) {
        this.paarcpros = pAAlarmRCAS;
        return this;
    }

    public PAProject addPaarcpro(PAAlarmRCA pAAlarmRCA) {
        paarcpros.add(pAAlarmRCA);
        pAAlarmRCA.setPaproarc(this);
        return this;
    }

    public PAProject removePaarcpro(PAAlarmRCA pAAlarmRCA) {
        paarcpros.remove(pAAlarmRCA);
        pAAlarmRCA.setPaproarc(null);
        return this;
    }

    public void setPaarcpros(Set<PAAlarmRCA> pAAlarmRCAS) {
        this.paarcpros = pAAlarmRCAS;
    }

    public Set<PANEDetails> getPanedpros() {
        return panedpros;
    }

    public PAProject panedpros(Set<PANEDetails> pANEDetails) {
        this.panedpros = pANEDetails;
        return this;
    }

    public PAProject addPanedpro(PANEDetails pANEDetails) {
        panedpros.add(pANEDetails);
        pANEDetails.setPaproned(this);
        return this;
    }

    public PAProject removePanedpro(PANEDetails pANEDetails) {
        panedpros.remove(pANEDetails);
        pANEDetails.setPaproned(null);
        return this;
    }

    public void setPanedpros(Set<PANEDetails> pANEDetails) {
        this.panedpros = pANEDetails;
    }

    public Set<PADataConnector> getPadcpros() {
        return padcpros;
    }

    public PAProject padcpros(Set<PADataConnector> pADataConnectors) {
        this.padcpros = pADataConnectors;
        return this;
    }

    public PAProject addPadcpro(PADataConnector pADataConnector) {
        padcpros.add(pADataConnector);
        pADataConnector.setPaprodc(this);
        return this;
    }

    public PAProject removePadcpro(PADataConnector pADataConnector) {
        padcpros.remove(pADataConnector);
        pADataConnector.setPaprodc(null);
        return this;
    }

    public void setPadcpros(Set<PADataConnector> pADataConnectors) {
        this.padcpros = pADataConnectors;
    }

    public Set<PAScheduler> getPaschpros() {
        return paschpros;
    }

    public PAProject paschpros(Set<PAScheduler> pASchedulers) {
        this.paschpros = pASchedulers;
        return this;
    }

    public PAProject addPaschpro(PAScheduler pAScheduler) {
        paschpros.add(pAScheduler);
        pAScheduler.setPaprosch(this);
        return this;
    }

    public PAProject removePaschpro(PAScheduler pAScheduler) {
        paschpros.remove(pAScheduler);
        pAScheduler.setPaprosch(null);
        return this;
    }

    public void setPaschpros(Set<PAScheduler> pASchedulers) {
        this.paschpros = pASchedulers;
    }
    
    public Set<User> getPausers() {
		return pausers;
	}

	public void setPausers(Set<User> pausers) {
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
        PAProject pAProject = (PAProject) o;
        if (pAProject.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pAProject.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PAProject{" +
            "id=" + id +
            ", projectname='" + projectname + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
