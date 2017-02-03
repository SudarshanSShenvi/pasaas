package com.pervazive.kheddah.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A PAErrorMessage.
 */
@Entity
@Table(name = "pa_errormessage")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PAErrorMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "errorcode")
    private String errorcode;

    @Column(name = "errormsg")
    private String errormsg;

    @Column(name = "errortime")
    private ZonedDateTime errortime;

    @Column(name = "erroruser")
    private String erroruser;

    @ManyToOne
    private PAOrganization paorgem;

    @ManyToOne
    private PAProject paproem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public PAErrorMessage errorcode(String errorcode) {
        this.errorcode = errorcode;
        return this;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public PAErrorMessage errormsg(String errormsg) {
        this.errormsg = errormsg;
        return this;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public ZonedDateTime getErrortime() {
        return errortime;
    }

    public PAErrorMessage errortime(ZonedDateTime errortime) {
        this.errortime = errortime;
        return this;
    }

    public void setErrortime(ZonedDateTime errortime) {
        this.errortime = errortime;
    }

    public String getErroruser() {
        return erroruser;
    }

    public PAErrorMessage erroruser(String erroruser) {
        this.erroruser = erroruser;
        return this;
    }

    public void setErroruser(String erroruser) {
        this.erroruser = erroruser;
    }

    public PAOrganization getPaorgem() {
        return paorgem;
    }

    public PAErrorMessage paorgem(PAOrganization pAOrganization) {
        this.paorgem = pAOrganization;
        return this;
    }

    public void setPaorgem(PAOrganization pAOrganization) {
        this.paorgem = pAOrganization;
    }

    public PAProject getPaproem() {
        return paproem;
    }

    public PAErrorMessage paproem(PAProject pAProject) {
        this.paproem = pAProject;
        return this;
    }

    public void setPaproem(PAProject pAProject) {
        this.paproem = pAProject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PAErrorMessage pAErrorMessage = (PAErrorMessage) o;
        if (pAErrorMessage.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pAErrorMessage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PAErrorMessage{" +
            "id=" + id +
            ", errorcode='" + errorcode + "'" +
            ", errormsg='" + errormsg + "'" +
            ", errortime='" + errortime + "'" +
            ", erroruser='" + erroruser + "'" +
            '}';
    }
}
