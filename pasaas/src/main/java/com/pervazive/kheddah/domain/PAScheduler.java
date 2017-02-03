package com.pervazive.kheddah.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.pervazive.kheddah.domain.enumeration.PAStatus;

/**
 * A PAScheduler.
 */
@Entity
@Table(name = "pa_scheduler")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PAScheduler implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "intervaltime")
    private Integer intervaltime;

    @Column(name = "hourval")
    private Integer hourval;

    @Column(name = "minutesval")
    private Integer minutesval;

    @Column(name = "runsunday")
    private Boolean runsunday;

    @Column(name = "runmonday")
    private Boolean runmonday;

    @Column(name = "runtuesday")
    private Boolean runtuesday;

    @Column(name = "runwednesday")
    private Boolean runwednesday;

    @Column(name = "runthursday")
    private Boolean runthursday;

    @Column(name = "runfriday")
    private Boolean runfriday;

    @Column(name = "dcname")
    private String dcname;

    @Column(name = "runsaturday")
    private Boolean runsaturday;

    @Enumerated(EnumType.STRING)
    @Column(name = "pastatus")
    private PAStatus pastatus;

    @ManyToOne
    private PAOrganization paorgsch;

    @ManyToOne
    private PAProject paprosch;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public PAScheduler type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIntervaltime() {
        return intervaltime;
    }

    public PAScheduler intervaltime(Integer intervaltime) {
        this.intervaltime = intervaltime;
        return this;
    }

    public void setIntervaltime(Integer intervaltime) {
        this.intervaltime = intervaltime;
    }

    public Integer getHourval() {
        return hourval;
    }

    public PAScheduler hourval(Integer hourval) {
        this.hourval = hourval;
        return this;
    }

    public void setHourval(Integer hourval) {
        this.hourval = hourval;
    }

    public Integer getMinutesval() {
        return minutesval;
    }

    public PAScheduler minutesval(Integer minutesval) {
        this.minutesval = minutesval;
        return this;
    }

    public void setMinutesval(Integer minutesval) {
        this.minutesval = minutesval;
    }

    public Boolean isRunsunday() {
        return runsunday;
    }

    public PAScheduler runsunday(Boolean runsunday) {
        this.runsunday = runsunday;
        return this;
    }

    public void setRunsunday(Boolean runsunday) {
        this.runsunday = runsunday;
    }

    public Boolean isRunmonday() {
        return runmonday;
    }

    public PAScheduler runmonday(Boolean runmonday) {
        this.runmonday = runmonday;
        return this;
    }

    public void setRunmonday(Boolean runmonday) {
        this.runmonday = runmonday;
    }

    public Boolean isRuntuesday() {
        return runtuesday;
    }

    public PAScheduler runtuesday(Boolean runtuesday) {
        this.runtuesday = runtuesday;
        return this;
    }

    public void setRuntuesday(Boolean runtuesday) {
        this.runtuesday = runtuesday;
    }

    public Boolean isRunwednesday() {
        return runwednesday;
    }

    public PAScheduler runwednesday(Boolean runwednesday) {
        this.runwednesday = runwednesday;
        return this;
    }

    public void setRunwednesday(Boolean runwednesday) {
        this.runwednesday = runwednesday;
    }

    public Boolean isRunthursday() {
        return runthursday;
    }

    public PAScheduler runthursday(Boolean runthursday) {
        this.runthursday = runthursday;
        return this;
    }

    public void setRunthursday(Boolean runthursday) {
        this.runthursday = runthursday;
    }

    public Boolean isRunfriday() {
        return runfriday;
    }

    public PAScheduler runfriday(Boolean runfriday) {
        this.runfriday = runfriday;
        return this;
    }

    public void setRunfriday(Boolean runfriday) {
        this.runfriday = runfriday;
    }

    public String getDcname() {
        return dcname;
    }

    public PAScheduler dcname(String dcname) {
        this.dcname = dcname;
        return this;
    }

    public void setDcname(String dcname) {
        this.dcname = dcname;
    }

    public Boolean isRunsaturday() {
        return runsaturday;
    }

    public PAScheduler runsaturday(Boolean runsaturday) {
        this.runsaturday = runsaturday;
        return this;
    }

    public void setRunsaturday(Boolean runsaturday) {
        this.runsaturday = runsaturday;
    }

    public PAStatus getPastatus() {
        return pastatus;
    }

    public PAScheduler pastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
        return this;
    }

    public void setPastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
    }

    public PAOrganization getPaorgsch() {
        return paorgsch;
    }

    public PAScheduler paorgsch(PAOrganization pAOrganization) {
        this.paorgsch = pAOrganization;
        return this;
    }

    public void setPaorgsch(PAOrganization pAOrganization) {
        this.paorgsch = pAOrganization;
    }

    public PAProject getPaprosch() {
        return paprosch;
    }

    public PAScheduler paprosch(PAProject pAProject) {
        this.paprosch = pAProject;
        return this;
    }

    public void setPaprosch(PAProject pAProject) {
        this.paprosch = pAProject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PAScheduler pAScheduler = (PAScheduler) o;
        if (pAScheduler.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pAScheduler.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PAScheduler{" +
            "id=" + id +
            ", type='" + type + "'" +
            ", intervaltime='" + intervaltime + "'" +
            ", hourval='" + hourval + "'" +
            ", minutesval='" + minutesval + "'" +
            ", runsunday='" + runsunday + "'" +
            ", runmonday='" + runmonday + "'" +
            ", runtuesday='" + runtuesday + "'" +
            ", runwednesday='" + runwednesday + "'" +
            ", runthursday='" + runthursday + "'" +
            ", runfriday='" + runfriday + "'" +
            ", dcname='" + dcname + "'" +
            ", runsaturday='" + runsaturday + "'" +
            ", pastatus='" + pastatus + "'" +
            '}';
    }
}
