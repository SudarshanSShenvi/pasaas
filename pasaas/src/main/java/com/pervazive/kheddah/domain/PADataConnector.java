package com.pervazive.kheddah.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.pervazive.kheddah.domain.enumeration.DCType;

import com.pervazive.kheddah.domain.enumeration.PAStatus;

/**
 * A PADataConnector.
 */
@Entity
@Table(name = "pa_dataconnector")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PADataConnector implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "dcname")
    private String dcname;

    @Enumerated(EnumType.STRING)
    @Column(name = "dctype")
    private DCType dctype;

    @Column(name = "urllink")
    private String urllink;

    @Column(name = "remoteuname")
    private String remoteuname;

    @Column(name = "remotepwd")
    private String remotepwd;

    @Column(name = "remoteip")
    private String remoteip;

    @Column(name = "port")
    private Integer port;

    @Column(name = "localpath")
    private String localpath;

    @Column(name = "jsrpwd")
    private String jsrpwd;

    @Column(name = "jsruser")
    private String jsruser;

    @Column(name = "destinationpath")
    private String destinationpath;

    @Column(name = "retrieve")
    private Integer retrieve;

    @Column(name = "retrievedays")
    private Integer retrievedays;

    @Column(name = "mode")
    private String mode;

    @Column(name = "datamode")
    private String datamode;

    @Enumerated(EnumType.STRING)
    @Column(name = "pastatus")
    private PAStatus pastatus;

    @ManyToOne
    private PAOrganization paorgdc;

    @ManyToOne
    private PAProject paprodc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDcname() {
        return dcname;
    }

    public PADataConnector dcname(String dcname) {
        this.dcname = dcname;
        return this;
    }

    public void setDcname(String dcname) {
        this.dcname = dcname;
    }

    public DCType getDctype() {
        return dctype;
    }

    public PADataConnector dctype(DCType dctype) {
        this.dctype = dctype;
        return this;
    }

    public void setDctype(DCType dctype) {
        this.dctype = dctype;
    }

    public String getUrllink() {
        return urllink;
    }

    public PADataConnector urllink(String urllink) {
        this.urllink = urllink;
        return this;
    }

    public void setUrllink(String urllink) {
        this.urllink = urllink;
    }

    public String getRemoteuname() {
        return remoteuname;
    }

    public PADataConnector remoteuname(String remoteuname) {
        this.remoteuname = remoteuname;
        return this;
    }

    public void setRemoteuname(String remoteuname) {
        this.remoteuname = remoteuname;
    }

    public String getRemotepwd() {
        return remotepwd;
    }

    public PADataConnector remotepwd(String remotepwd) {
        this.remotepwd = remotepwd;
        return this;
    }

    public void setRemotepwd(String remotepwd) {
        this.remotepwd = remotepwd;
    }

    public String getRemoteip() {
        return remoteip;
    }

    public PADataConnector remoteip(String remoteip) {
        this.remoteip = remoteip;
        return this;
    }

    public void setRemoteip(String remoteip) {
        this.remoteip = remoteip;
    }

    public Integer getPort() {
        return port;
    }

    public PADataConnector port(Integer port) {
        this.port = port;
        return this;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getLocalpath() {
        return localpath;
    }

    public PADataConnector localpath(String localpath) {
        this.localpath = localpath;
        return this;
    }

    public void setLocalpath(String localpath) {
        this.localpath = localpath;
    }

    public String getJsrpwd() {
        return jsrpwd;
    }

    public PADataConnector jsrpwd(String jsrpwd) {
        this.jsrpwd = jsrpwd;
        return this;
    }

    public void setJsrpwd(String jsrpwd) {
        this.jsrpwd = jsrpwd;
    }

    public String getJsruser() {
        return jsruser;
    }

    public PADataConnector jsruser(String jsruser) {
        this.jsruser = jsruser;
        return this;
    }

    public void setJsruser(String jsruser) {
        this.jsruser = jsruser;
    }

    public String getDestinationpath() {
        return destinationpath;
    }

    public PADataConnector destinationpath(String destinationpath) {
        this.destinationpath = destinationpath;
        return this;
    }

    public void setDestinationpath(String destinationpath) {
        this.destinationpath = destinationpath;
    }

    public Integer getRetrieve() {
        return retrieve;
    }

    public PADataConnector retrieve(Integer retrieve) {
        this.retrieve = retrieve;
        return this;
    }

    public void setRetrieve(Integer retrieve) {
        this.retrieve = retrieve;
    }

    public Integer getRetrievedays() {
        return retrievedays;
    }

    public PADataConnector retrievedays(Integer retrievedays) {
        this.retrievedays = retrievedays;
        return this;
    }

    public void setRetrievedays(Integer retrievedays) {
        this.retrievedays = retrievedays;
    }

    public String getMode() {
        return mode;
    }

    public PADataConnector mode(String mode) {
        this.mode = mode;
        return this;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getDatamode() {
        return datamode;
    }

    public PADataConnector datamode(String datamode) {
        this.datamode = datamode;
        return this;
    }

    public void setDatamode(String datamode) {
        this.datamode = datamode;
    }

    public PAStatus getPastatus() {
        return pastatus;
    }

    public PADataConnector pastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
        return this;
    }

    public void setPastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
    }

    public PAOrganization getPaorgdc() {
        return paorgdc;
    }

    public PADataConnector paorgdc(PAOrganization pAOrganization) {
        this.paorgdc = pAOrganization;
        return this;
    }

    public void setPaorgdc(PAOrganization pAOrganization) {
        this.paorgdc = pAOrganization;
    }

    public PAProject getPaprodc() {
        return paprodc;
    }

    public PADataConnector paprodc(PAProject pAProject) {
        this.paprodc = pAProject;
        return this;
    }

    public void setPaprodc(PAProject pAProject) {
        this.paprodc = pAProject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PADataConnector pADataConnector = (PADataConnector) o;
        if (pADataConnector.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pADataConnector.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PADataConnector{" +
            "id=" + id +
            ", dcname='" + dcname + "'" +
            ", dctype='" + dctype + "'" +
            ", urllink='" + urllink + "'" +
            ", remoteuname='" + remoteuname + "'" +
            ", remotepwd='" + remotepwd + "'" +
            ", remoteip='" + remoteip + "'" +
            ", port='" + port + "'" +
            ", localpath='" + localpath + "'" +
            ", jsrpwd='" + jsrpwd + "'" +
            ", jsruser='" + jsruser + "'" +
            ", destinationpath='" + destinationpath + "'" +
            ", retrieve='" + retrieve + "'" +
            ", retrievedays='" + retrievedays + "'" +
            ", mode='" + mode + "'" +
            ", datamode='" + datamode + "'" +
            ", pastatus='" + pastatus + "'" +
            '}';
    }
}
