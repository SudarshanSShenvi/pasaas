package com.pervazive.kheddah.service.dto;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;

import com.pervazive.kheddah.domain.PABusinessPlan;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAProject;
import com.pervazive.kheddah.domain.User;
import com.pervazive.kheddah.domain.enumeration.PAStatus;

public class PAOrganizationDTO {
	
	public PAOrganizationDTO(Long id, String organization, ZonedDateTime validfrom, ZonedDateTime validto,
			PAStatus pastatus, PABusinessPlan pabporg, Set<String> pausers, Set<String> paprojects, String industrytype, String website) {
		super();
		this.id = id;
		this.organization = organization;
		this.validfrom = validfrom;
		this.validto = validto;
		this.pastatus = pastatus;
		this.pabporg = pabporg;
		this.pausers = pausers;
		this.paprojects = paprojects;
		this.industrytype = industrytype;
		this.website = website;
	}
    
    public PAOrganizationDTO(PAOrganization paOrganization){
    	this(paOrganization.getId(), paOrganization.getOrganization(), paOrganization.getValidfrom(), paOrganization.getValidto(), paOrganization.getPastatus(), 
    			paOrganization.getPabporg(), paOrganization.getPAUsers().stream().map(User::getLogin).collect(Collectors.toSet()), 
    			paOrganization.getPaproorgs().stream().map(PAProject::getProjectname).collect(Collectors.toSet()), paOrganization.getIndustrytype(), paOrganization.getWebsite());
    }
    
    public PAOrganizationDTO(){
    	
    }
    private Long id;
    private String organization;
    
	private ZonedDateTime validfrom;
    private ZonedDateTime validto;
    private PAStatus pastatus;
    private PABusinessPlan pabporg;
    private Set<String> pausers;
    private Set<String> paprojects;
    private String industrytype;
    private String website;
    
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public ZonedDateTime getValidfrom() {
		return validfrom;
	}

	public void setValidfrom(ZonedDateTime validfrom) {
		this.validfrom = validfrom;
	}

	public ZonedDateTime getValidto() {
		return validto;
	}

	public void setValidto(ZonedDateTime validto) {
		this.validto = validto;
	}

	public PAStatus getPastatus() {
		return pastatus;
	}

	public void setPastatus(PAStatus pastatus) {
		this.pastatus = pastatus;
	}

	public PABusinessPlan getPabporg() {
		return pabporg;
	}

	public void setPabporg(PABusinessPlan pabporg) {
		this.pabporg = pabporg;
	}

	public Set<String> getPausers() {
		return pausers;
	}

	public void setPausers(Set<String> pausers) {
		this.pausers = pausers;
	}
	
	public Set<String> getPaprojects() {
		return paprojects;
	}

	public void setPaprojects(Set<String> paprojects) {
		this.paprojects = paprojects;
	}

	public String getIndustrytype() {
		return industrytype;
	}

	public void setIndustrytype(String industrytype) {
		this.industrytype = industrytype;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
}
