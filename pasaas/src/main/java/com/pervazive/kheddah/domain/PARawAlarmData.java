package com.pervazive.kheddah.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A PARawAlarmData.
 */
@Entity
@Table(name = "pa_rawalarm_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PARawAlarmData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "alarmno")
    private Integer alarmno;

    @Column(name = "distname")
    private String distname;

    @Column(name = "starteddate")
    private ZonedDateTime starteddate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAlarmno() {
        return alarmno;
    }

    public PARawAlarmData alarmno(Integer alarmno) {
        this.alarmno = alarmno;
        return this;
    }

    public void setAlarmno(Integer alarmno) {
        this.alarmno = alarmno;
    }

    public String getDistname() {
        return distname;
    }

    public PARawAlarmData distname(String distname) {
        this.distname = distname;
        return this;
    }

    public void setDistname(String distname) {
        this.distname = distname;
    }

    public ZonedDateTime getStarteddate() {
        return starteddate;
    }

    public PARawAlarmData starteddate(ZonedDateTime starteddate) {
        this.starteddate = starteddate;
        return this;
    }

    public void setStarteddate(ZonedDateTime starteddate) {
        this.starteddate = starteddate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PARawAlarmData pARawAlarmData = (PARawAlarmData) o;
        if (pARawAlarmData.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pARawAlarmData.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PARawAlarmData{" +
            "id=" + id +
            ", alarmno='" + alarmno + "'" +
            ", distname='" + distname + "'" +
            ", starteddate='" + starteddate + "'" +
            '}';
    }
}
