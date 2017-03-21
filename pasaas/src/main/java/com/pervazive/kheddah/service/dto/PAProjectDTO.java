package com.pervazive.kheddah.service.dto;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAProject;
import com.pervazive.kheddah.domain.User;
import com.pervazive.kheddah.domain.enumeration.PAStatus;

public class PAProjectDTO {
	
	public PAProjectDTO(Long id, String projectname, String description, PAOrganization paorgpro, 
			Set<String> pausers, String objectentity, String timeseriesentity, String feedindateformat, String feedoutdateformat,
			boolean feedfirstlineheader, String feedskipindexes, String rollindateformat, String rolloutdateformat,
			String rollseriesgroupindex, ZonedDateTime rollseriesstart, ZonedDateTime rollseriesend,
			ZonedDateTime rollseriesnxt, Integer patternfldindex, Integer patterntraininterval,
			Integer patternpredictinterval, Integer patternintervalthreshhold, PAStatus pastatus,
			String createdBy, ZonedDateTime createdDate, String lastModifiedBy, ZonedDateTime lastModifiedDate) {
		super();
		this.objectentity = objectentity;
		this.timeseriesentity = timeseriesentity;
		this.feedindateformat = feedindateformat;
		this.feedoutdateformat = feedoutdateformat;
		this.feedfirstlineheader = feedfirstlineheader;
		this.feedskipindexes = feedskipindexes;
		this.rollindateformat = rollindateformat;
		this.rolloutdateformat = rolloutdateformat;
		this.rollseriesgroupindex = rollseriesgroupindex;
		this.rollseriesstart = rollseriesstart;
		this.rollseriesend = rollseriesend;
		this.rollseriesnxt = rollseriesnxt;
		this.patternfldindex = patternfldindex;
		this.patterntraininterval = patterntraininterval;
		this.patternpredictinterval = patternpredictinterval;
		this.patternintervalthreshhold = patternintervalthreshhold;
		this.pastatus = pastatus;
		this.id = id;
		this.projectname = projectname;
		this.description = description;
		this.paorgpro = paorgpro;
		this.pausers = pausers;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.lastModifiedBy = lastModifiedBy;
		this.lastModifiedDate = lastModifiedDate;
	}

	public PAProjectDTO(PAProject paProject){
		this(paProject.getId(), paProject.getProjectname(), paProject.getDescription(), paProject.getPaorgpro(),
				paProject.getPausers().stream().map(User::getLogin).collect(Collectors.toSet()), paProject.getObjectentity(), paProject.getTimeseriesentity(), 
				paProject.getFeedindateformat(), paProject.getFeedoutdateformat(), paProject.getFeedfirstlineheader(), paProject.getFeedskipindexes(),
				paProject.getRollindateformat(), paProject.getRolloutdateformat(), paProject.getRollseriesgroupindex(), paProject.getRollseriesstart(),
				paProject.getRollseriesend(), paProject.getRollseriesnxt(), paProject.getPatternfldindex(), paProject.getPatterntraininterval(),
				paProject.getPatternpredictinterval(), paProject.getPatternintervalthreshhold(), paProject.getPastatus(), 
				paProject.getCreatedBy(), paProject.getCreatedDate(), paProject.getLastModifiedBy(), paProject.getLastModifiedDate());
	}
	
	public PAProjectDTO(){
	}
	
	
    private String objectentity;		// ==> 13:6 (Column identifier)
   	private String timeseriesentity; 	// ==> 30   (Time identifier )
   	private String feedindateformat; 	// ==> UNIXTIME
   	private String feedoutdateformat;	// ==> CUSTOMDATE#yyyy-MM-dd HH:mm:ss
    private boolean feedfirstlineheader = false; 	//	==> FirstLine Header true/false
 	private String feedskipindexes;		// ==> skip indexes out of computations
 	private String rollindateformat; 	// ==> CUSTOMDATE#yyyy-MM-dd HH:mm:ss
   	private String rolloutdateformat; 	// ==> CUSTOMDATE#yyyy-MM-dd HH:mm:ss
   	private String rollseriesgroupindex;	// ==> 2
   	private ZonedDateTime rollseriesstart;	//	==> 2015-03-01 00:00:00
   	private ZonedDateTime rollseriesend;		//==> 2015-03-31 00:00:00
   	private ZonedDateTime rollseriesnxt;		//==> 2015-03-02 00:00:00
 	private Integer patternfldindex;	//==> 2
 	private Integer patterntraininterval;	//==> 5
 	private Integer patternpredictinterval;	//==> 4
 	private Integer patternintervalthreshhold; //==> 0
    private PAStatus pastatus;
	
	private Long id;
	private String projectname;
	private String description;
	private PAOrganization paorgpro;
	private Set<String> pausers = new HashSet<>();
	
	private String createdBy;
	private ZonedDateTime createdDate;
	private String lastModifiedBy;
	private ZonedDateTime lastModifiedDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public PAOrganization getPaorgpro() {
		return paorgpro;
	}
	public void setPaorgpro(PAOrganization paorgpro) {
		this.paorgpro = paorgpro;
	}
	public Set<String> getPausers() {
		return pausers;
	}
	public void setPausers(Set<String> pausers) {
		this.pausers = pausers;
	}

	public String getObjectentity() {
		return objectentity;
	}

	public void setObjectentity(String objectentity) {
		this.objectentity = objectentity;
	}

	public String getTimeseriesentity() {
		return timeseriesentity;
	}

	public void setTimeseriesentity(String timeseriesentity) {
		this.timeseriesentity = timeseriesentity;
	}

	public String getFeedindateformat() {
		return feedindateformat;
	}

	public void setFeedindateformat(String feedindateformat) {
		this.feedindateformat = feedindateformat;
	}

	public String getFeedoutdateformat() {
		return feedoutdateformat;
	}

	public void setFeedoutdateformat(String feedoutdateformat) {
		this.feedoutdateformat = feedoutdateformat;
	}

	public boolean isFeedfirstlineheader() {
		return feedfirstlineheader;
	}

	public void setFeedfirstlineheader(boolean feedfirstlineheader) {
		this.feedfirstlineheader = feedfirstlineheader;
	}

	public String getFeedskipindexes() {
		return feedskipindexes;
	}

	public void setFeedskipindexes(String feedskipindexes) {
		this.feedskipindexes = feedskipindexes;
	}

	public String getRollindateformat() {
		return rollindateformat;
	}

	public void setRollindateformat(String rollindateformat) {
		this.rollindateformat = rollindateformat;
	}

	public String getRolloutdateformat() {
		return rolloutdateformat;
	}

	public void setRolloutdateformat(String rolloutdateformat) {
		this.rolloutdateformat = rolloutdateformat;
	}

	public String getRollseriesgroupindex() {
		return rollseriesgroupindex;
	}

	public void setRollseriesgroupindex(String rollseriesgroupindex) {
		this.rollseriesgroupindex = rollseriesgroupindex;
	}

	public ZonedDateTime getRollseriesstart() {
		return rollseriesstart;
	}

	public void setRollseriesstart(ZonedDateTime rollseriesstart) {
		this.rollseriesstart = rollseriesstart;
	}

	public ZonedDateTime getRollseriesend() {
		return rollseriesend;
	}

	public void setRollseriesend(ZonedDateTime rollseriesend) {
		this.rollseriesend = rollseriesend;
	}

	public ZonedDateTime getRollseriesnxt() {
		return rollseriesnxt;
	}

	public void setRollseriesnxt(ZonedDateTime rollseriesnxt) {
		this.rollseriesnxt = rollseriesnxt;
	}

	public Integer getPatternfldindex() {
		return patternfldindex;
	}

	public void setPatternfldindex(Integer patternfldindex) {
		this.patternfldindex = patternfldindex;
	}

	public Integer getPatterntraininterval() {
		return patterntraininterval;
	}

	public void setPatterntraininterval(Integer patterntraininterval) {
		this.patterntraininterval = patterntraininterval;
	}

	public Integer getPatternpredictinterval() {
		return patternpredictinterval;
	}

	public void setPatternpredictinterval(Integer patternpredictinterval) {
		this.patternpredictinterval = patternpredictinterval;
	}

	public Integer getPatternintervalthreshhold() {
		return patternintervalthreshhold;
	}

	public void setPatternintervalthreshhold(Integer patternintervalthreshhold) {
		this.patternintervalthreshhold = patternintervalthreshhold;
	}

	public PAStatus getPastatus() {
		return pastatus;
	}

	public void setPastatus(PAStatus pastatus) {
		this.pastatus = pastatus;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public ZonedDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(ZonedDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public ZonedDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
}
