package com.pervazive.kheddah.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.pervazive.kheddah.domain.enumeration.PAStatus;

/**
 * A PAPredictionScore.
 */
@Entity
@Table(name = "pa_prediction_score")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PAPredictionScore implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "dist")
    private String dist;

    @Column(name = "alarmno")
    private Integer alarmno;

    @Column(name = "bcount")
    private Integer bcount;

    @Column(name = "ccount")
    private Integer ccount;

    @Column(name = "bscore", precision=4)
    private Float bscore;

    @Column(name = "cscore", precision=4)
    private Float cscore;

    @Column(name = "createdon")
    private ZonedDateTime createdon;

    @Column(name = "zaxis")
    private Integer zaxis;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "severity")
    private String severity;

    @Column(name = "painterval")
    private String painterval;

    @Enumerated(EnumType.STRING)
    @Column(name = "pastatus")
    private PAStatus pastatus;

    @ManyToOne
    private PAOrganization paorgps;

    @ManyToOne
    private PAProject paprops;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDist() {
        return dist;
    }

    public PAPredictionScore dist(String dist) {
        this.dist = dist;
        return this;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public Integer getAlarmno() {
        return alarmno;
    }

    public PAPredictionScore alarmno(Integer alarmno) {
        this.alarmno = alarmno;
        return this;
    }

    public void setAlarmno(Integer alarmno) {
        this.alarmno = alarmno;
    }

    public Integer getBcount() {
        return bcount;
    }

    public PAPredictionScore bcount(Integer bcount) {
        this.bcount = bcount;
        return this;
    }

    public void setBcount(Integer bcount) {
        this.bcount = bcount;
    }

    public Integer getCcount() {
        return ccount;
    }

    public PAPredictionScore ccount(Integer ccount) {
        this.ccount = ccount;
        return this;
    }

    public void setCcount(Integer ccount) {
        this.ccount = ccount;
    }

    public Float getBscore() {
        return bscore;
    }

    public PAPredictionScore bscore(Float bscore) {
        this.bscore = bscore;
        return this;
    }

    public void setBscore(Float bscore) {
        this.bscore = bscore;
    }

    public Float getCscore() {
        return cscore;
    }

    public PAPredictionScore cscore(Float cscore) {
        this.cscore = cscore;
        return this;
    }

    public void setCscore(Float cscore) {
        this.cscore = cscore;
    }

    public ZonedDateTime getCreatedon() {
        return createdon;
    }

    public PAPredictionScore createdon(ZonedDateTime createdon) {
        this.createdon = createdon;
        return this;
    }

    public void setCreatedon(ZonedDateTime createdon) {
        this.createdon = createdon;
    }

    public Integer getZaxis() {
        return zaxis;
    }

    public PAPredictionScore zaxis(Integer zaxis) {
        this.zaxis = zaxis;
        return this;
    }

    public void setZaxis(Integer zaxis) {
        this.zaxis = zaxis;
    }

    public LocalDate getDate() {
        return date;
    }

    public PAPredictionScore date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getSeverity() {
        return severity;
    }

    public PAPredictionScore severity(String severity) {
        this.severity = severity;
        return this;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getPainterval() {
        return painterval;
    }

    public PAPredictionScore painterval(String painterval) {
        this.painterval = painterval;
        return this;
    }

    public void setPainterval(String painterval) {
        this.painterval = painterval;
    }

    public PAStatus getPastatus() {
        return pastatus;
    }

    public PAPredictionScore pastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
        return this;
    }

    public void setPastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
    }

    public PAOrganization getPaorgps() {
        return paorgps;
    }

    public PAPredictionScore paorgps(PAOrganization pAOrganization) {
        this.paorgps = pAOrganization;
        return this;
    }

    public void setPaorgps(PAOrganization pAOrganization) {
        this.paorgps = pAOrganization;
    }

    public PAProject getPaprops() {
        return paprops;
    }

    public PAPredictionScore paprops(PAProject pAProject) {
        this.paprops = pAProject;
        return this;
    }

    public void setPaprops(PAProject pAProject) {
        this.paprops = pAProject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PAPredictionScore pAPredictionScore = (PAPredictionScore) o;
        if (pAPredictionScore.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pAPredictionScore.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PAPredictionScore{" +
            "id=" + id +
            ", dist='" + dist + "'" +
            ", alarmno='" + alarmno + "'" +
            ", bcount='" + bcount + "'" +
            ", ccount='" + ccount + "'" +
            ", bscore='" + bscore + "'" +
            ", cscore='" + cscore + "'" +
            ", createdon='" + createdon + "'" +
            ", zaxis='" + zaxis + "'" +
            ", date='" + date + "'" +
            ", severity='" + severity + "'" +
            ", painterval='" + painterval + "'" +
            ", pastatus='" + pastatus + "'" +
            '}';
    }
}
