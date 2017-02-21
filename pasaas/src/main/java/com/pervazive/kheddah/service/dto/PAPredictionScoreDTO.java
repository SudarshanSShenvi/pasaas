package com.pervazive.kheddah.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import com.pervazive.kheddah.domain.PAPredictionScore;

public class PAPredictionScoreDTO implements Serializable {

    public PAPredictionScoreDTO(Long id, String dist, Integer alarmno, Integer bcount, Integer ccount, Float bscore,
			Float cscore, ZonedDateTime createdon, LocalDate date, String severity) {
		super();
		this.id = id;
		this.dist = dist;
		this.alarmno = alarmno;
		this.bcount = bcount;
		this.ccount = ccount;
		this.bscore = bscore;
		this.cscore = cscore;
		this.createdon = createdon;
		this.date = date;
		this.severity = severity;
	}
    public PAPredictionScoreDTO(PAPredictionScore paPredictionScore){
    	this(paPredictionScore.getId(),
    			paPredictionScore.getDist(),
    			paPredictionScore.getAlarmno(),
    			paPredictionScore.getBcount(),
    			paPredictionScore.getCcount(),
    			paPredictionScore.getBscore(),
    			paPredictionScore.getCscore(),
    			paPredictionScore.getCreatedon(),
    			paPredictionScore.getDate(),
    			paPredictionScore.getSeverity());
    }
    

	private static final long serialVersionUID = 1L;

    private Long id;
    private String dist;
    private Integer alarmno;
    private Integer bcount;
    private Integer ccount;
    
    private Float bscore;
    private Float cscore;
    private ZonedDateTime createdon;
    private LocalDate date;
    private String severity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDist() {
        return dist;
    }

    public PAPredictionScoreDTO dist(String dist) {
        this.dist = dist;
        return this;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public Integer getAlarmno() {
        return alarmno;
    }

    public PAPredictionScoreDTO alarmno(Integer alarmno) {
        this.alarmno = alarmno;
        return this;
    }

    public void setAlarmno(Integer alarmno) {
        this.alarmno = alarmno;
    }

    public Integer getBcount() {
        return bcount;
    }

    public PAPredictionScoreDTO bcount(Integer bcount) {
        this.bcount = bcount;
        return this;
    }

    public void setBcount(Integer bcount) {
        this.bcount = bcount;
    }

    public Integer getCcount() {
        return ccount;
    }

    public PAPredictionScoreDTO ccount(Integer ccount) {
        this.ccount = ccount;
        return this;
    }

    public void setCcount(Integer ccount) {
        this.ccount = ccount;
    }

    public Float getBscore() {
        return bscore;
    }

    public PAPredictionScoreDTO bscore(Float bscore) {
        this.bscore = bscore;
        return this;
    }

    public void setBscore(Float bscore) {
        this.bscore = bscore;
    }

    public Float getCscore() {
        return cscore;
    }

    public PAPredictionScoreDTO cscore(Float cscore) {
        this.cscore = cscore;
        return this;
    }

    public void setCscore(Float cscore) {
        this.cscore = cscore;
    }

    public ZonedDateTime getCreatedon() {
        return createdon;
    }

    public PAPredictionScoreDTO createdon(ZonedDateTime createdon) {
        this.createdon = createdon;
        return this;
    }

    public void setCreatedon(ZonedDateTime createdon) {
        this.createdon = createdon;
    }

    public LocalDate getDate() {
        return date;
    }

    public PAPredictionScoreDTO date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getSeverity() {
        return severity;
    }

    public PAPredictionScoreDTO severity(String severity) {
        this.severity = severity;
        return this;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
}
