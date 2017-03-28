package com.pervazive.kheddah.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.pervazive.kheddah.domain.enumeration.PAStatus;

/**
 * A PAReport.
 */
@Entity
@Table(name = "pa_report")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PAReport extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "reporttype")
    private String reporttype;

    @Column(name = "reportname")
    private String reportname;

    @Column(name = "reportparms")
    private String reportparms;

    @Enumerated(EnumType.STRING)
    @Column(name = "pastatus")
    private PAStatus pastatus;
    
    @Lob
    @Column(name = "reportfile")
    private byte[] reportfile; // expression.txt

    @Column(name = "reportfile_content_type")
    private String reportfileContentType;
    
    @Column(name = "paproject_id")
    private Long projectId;

    @ManyToOne
    private PAOrganization paorgrep;
    
/*    @ManyToOne
    private PAProject paprorep;*/


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReporttype() {
        return reporttype;
    }

    public PAReport reporttype(String reporttype) {
        this.reporttype = reporttype;
        return this;
    }

    public void setReporttype(String reporttype) {
        this.reporttype = reporttype;
    }

    public String getReportname() {
        return reportname;
    }

    public PAReport reportname(String reportname) {
        this.reportname = reportname;
        return this;
    }

    public void setReportname(String reportname) {
        this.reportname = reportname;
    }

    public String getReportparms() {
        return reportparms;
    }

    public PAReport reportparms(String reportparms) {
        this.reportparms = reportparms;
        return this;
    }

    public void setReportparms(String reportparms) {
        this.reportparms = reportparms;
    }

    public PAStatus getPastatus() {
        return pastatus;
    }

    public PAReport pastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
        return this;
    }

    public void setPastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
    }

    public PAOrganization getPaorgrep() {
        return paorgrep;
    }

    public PAReport paorgrep(PAOrganization pAOrganization) {
        this.paorgrep = pAOrganization;
        return this;
    }

    public void setPaorgrep(PAOrganization pAOrganization) {
        this.paorgrep = pAOrganization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PAReport pAReport = (PAReport) o;
        if (pAReport.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pAReport.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PAReport{" +
            "id=" + id +
            ", reporttype='" + reporttype + "'" +
            ", reportname='" + reportname + "'" +
            ", reportparms='" + reportparms + "'" +
            ", pastatus='" + pastatus + "'" +
            '}';
    }

	public byte[] getReportfile() {
		return reportfile;
	}

	public void setReportfile(byte[] reportfile) {
		this.reportfile = reportfile;
	}

	public String getReportfileContentType() {
		return reportfileContentType;
	}

	public void setReportfileContentType(String reportfileContentType) {
		this.reportfileContentType = reportfileContentType;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

}
