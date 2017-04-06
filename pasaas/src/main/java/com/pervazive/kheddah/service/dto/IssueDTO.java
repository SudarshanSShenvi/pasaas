package com.pervazive.kheddah.service.dto;

import java.time.ZonedDateTime;

import com.pervazive.kheddah.domain.Issues;

public class IssueDTO extends Issues {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public IssueDTO() {
	}
	
	public IssueDTO(String createdBy, ZonedDateTime createdDate, String lastModifiedBy,
			ZonedDateTime lastModifiedDate) {
		super();
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.lastModifiedBy = lastModifiedBy;
		this.lastModifiedDate = lastModifiedDate;
	}
	
	public IssueDTO(Issues issues) {
		this(issues.getCreatedBy(), issues.getCreatedDate(),
				issues.getLastModifiedBy(), issues.getLastModifiedDate());
	}
	
	private String createdBy;
    private ZonedDateTime createdDate;
    private String lastModifiedBy;
    private ZonedDateTime lastModifiedDate;
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
