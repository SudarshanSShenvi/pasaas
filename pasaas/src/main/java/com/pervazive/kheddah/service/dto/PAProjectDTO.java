package com.pervazive.kheddah.service.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.pervazive.kheddah.domain.PAProject;
import com.pervazive.kheddah.domain.User;

public class PAProjectDTO {
	
	public PAProjectDTO(Long id, String projectname, String description, Set<String> pausers) {
		super();
		this.id = id;
		this.projectname = projectname;
		this.description = description;
		
		this.pausers = pausers;
	}
	
	public PAProjectDTO(){
		
	}
	
	public PAProjectDTO(PAProject paProjectDTO){
		this(paProjectDTO.getId(), paProjectDTO.getProjectname(), paProjectDTO.getDescription(), 
				paProjectDTO.getPausers().stream().map(User::getLogin).collect(Collectors.toSet()));
	}
	
	
	
	private Long id;
	private String projectname;
	private String description;
	//private PAOrganization paorgpro;
	private Set<String> pausers = new HashSet<>();
	
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
	/*public PAOrganization getPaorgpro() {
		return paorgpro;
	}
	public void setPaorgpro(PAOrganization paorgpro) {
		this.paorgpro = paorgpro;
	}*/
	public Set<String> getPausers() {
		return pausers;
	}
	public void setPausers(Set<String> pausers) {
		this.pausers = pausers;
	}
}
