package com.pervazive.kheddah.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.pervazive.kheddah.domain.enumeration.PAStatus;

/**
 * A PAFileUpload.
 */
@Entity
@Table(name = "pa_fileupload")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PAFileUpload implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "remotepwd")
    private String remotepwd;

    @Column(name = "portno")
    private Integer portno;

    @Column(name = "filepath")
    private String filepath;

    @Column(name = "transfertype")
    private String transfertype;

    @Column(name = "remoteipaddr")
    private String remoteipaddr;

    @Column(name = "remoteuser")
    private String remoteuser;

    @Column(name = "scheduledprocess")
    private Integer scheduledprocess;

    @Column(name = "mapreduce")
    private String mapreduce;

    @Column(name = "filetype")
    private String filetype;

    @Column(name = "customseparator")
    private String customseparator;

    @Enumerated(EnumType.STRING)
    @Column(name = "pastatus")
    private PAStatus pastatus;

    @ManyToOne
    private PAOrganization paorgfu;

    @ManyToOne
    private PAProject paprofu;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemotepwd() {
        return remotepwd;
    }

    public PAFileUpload remotepwd(String remotepwd) {
        this.remotepwd = remotepwd;
        return this;
    }

    public void setRemotepwd(String remotepwd) {
        this.remotepwd = remotepwd;
    }

    public Integer getPortno() {
        return portno;
    }

    public PAFileUpload portno(Integer portno) {
        this.portno = portno;
        return this;
    }

    public void setPortno(Integer portno) {
        this.portno = portno;
    }

    public String getFilepath() {
        return filepath;
    }

    public PAFileUpload filepath(String filepath) {
        this.filepath = filepath;
        return this;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getTransfertype() {
        return transfertype;
    }

    public PAFileUpload transfertype(String transfertype) {
        this.transfertype = transfertype;
        return this;
    }

    public void setTransfertype(String transfertype) {
        this.transfertype = transfertype;
    }

    public String getRemoteipaddr() {
        return remoteipaddr;
    }

    public PAFileUpload remoteipaddr(String remoteipaddr) {
        this.remoteipaddr = remoteipaddr;
        return this;
    }

    public void setRemoteipaddr(String remoteipaddr) {
        this.remoteipaddr = remoteipaddr;
    }

    public String getRemoteuser() {
        return remoteuser;
    }

    public PAFileUpload remoteuser(String remoteuser) {
        this.remoteuser = remoteuser;
        return this;
    }

    public void setRemoteuser(String remoteuser) {
        this.remoteuser = remoteuser;
    }

    public Integer getScheduledprocess() {
        return scheduledprocess;
    }

    public PAFileUpload scheduledprocess(Integer scheduledprocess) {
        this.scheduledprocess = scheduledprocess;
        return this;
    }

    public void setScheduledprocess(Integer scheduledprocess) {
        this.scheduledprocess = scheduledprocess;
    }

    public String getMapreduce() {
        return mapreduce;
    }

    public PAFileUpload mapreduce(String mapreduce) {
        this.mapreduce = mapreduce;
        return this;
    }

    public void setMapreduce(String mapreduce) {
        this.mapreduce = mapreduce;
    }

    public String getFiletype() {
        return filetype;
    }

    public PAFileUpload filetype(String filetype) {
        this.filetype = filetype;
        return this;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getCustomseparator() {
        return customseparator;
    }

    public PAFileUpload customseparator(String customseparator) {
        this.customseparator = customseparator;
        return this;
    }

    public void setCustomseparator(String customseparator) {
        this.customseparator = customseparator;
    }

    public PAStatus getPastatus() {
        return pastatus;
    }

    public PAFileUpload pastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
        return this;
    }

    public void setPastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
    }

    public PAOrganization getPaorgfu() {
        return paorgfu;
    }

    public PAFileUpload paorgfu(PAOrganization pAOrganization) {
        this.paorgfu = pAOrganization;
        return this;
    }

    public void setPaorgfu(PAOrganization pAOrganization) {
        this.paorgfu = pAOrganization;
    }

    public PAProject getPaprofu() {
        return paprofu;
    }

    public PAFileUpload paprofu(PAProject pAProject) {
        this.paprofu = pAProject;
        return this;
    }

    public void setPaprofu(PAProject pAProject) {
        this.paprofu = pAProject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PAFileUpload pAFileUpload = (PAFileUpload) o;
        if (pAFileUpload.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pAFileUpload.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PAFileUpload{" +
            "id=" + id +
            ", remotepwd='" + remotepwd + "'" +
            ", portno='" + portno + "'" +
            ", filepath='" + filepath + "'" +
            ", transfertype='" + transfertype + "'" +
            ", remoteipaddr='" + remoteipaddr + "'" +
            ", remoteuser='" + remoteuser + "'" +
            ", scheduledprocess='" + scheduledprocess + "'" +
            ", mapreduce='" + mapreduce + "'" +
            ", filetype='" + filetype + "'" +
            ", customseparator='" + customseparator + "'" +
            ", pastatus='" + pastatus + "'" +
            '}';
    }
}
