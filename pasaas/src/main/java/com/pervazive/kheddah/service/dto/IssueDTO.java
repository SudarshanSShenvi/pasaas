package com.pervazive.kheddah.service.dto;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;

import com.pervazive.kheddah.domain.Issues;
import com.pervazive.kheddah.domain.enumeration.CategoryType;

public class IssueDTO extends Issues {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public IssueDTO() {
	}
	
	public IssueDTO(String createdBy, ZonedDateTime createdDate, String lastModifiedBy,
			ZonedDateTime lastModifiedDate, Long id, CategoryType category, String description,
    byte[] attachment, String attachmentContentType, String details) {
		super();
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.lastModifiedBy = lastModifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.id = id;
		this.category = category;
	    this.description = description;
	    this.attachment = attachment;
	    this.attachmentContentType = attachmentContentType;
	    this.details = details;
	}
	
	public IssueDTO(Issues issues) {
		this(issues.getCreatedBy(), issues.getCreatedDate(),
				issues.getLastModifiedBy(), issues.getLastModifiedDate(), issues.getId(),
				issues.getCategory(), issues.getDescription(), issues.getAttachment(), issues.getAttachmentContentType(), issues.getDetails());
	}
	
	private String createdBy;
    private ZonedDateTime createdDate;
    private String lastModifiedBy;
    private ZonedDateTime lastModifiedDate;
    
    private Long id;
    private CategoryType category;
    private String description;
    private byte[] attachment;
    private String attachmentContentType;
    private String details;
    
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CategoryType getCategory() {
		return category;
	}

	public void setCategory(CategoryType category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getAttachment() {
		return attachment;
	}

	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}

	public String getAttachmentContentType() {
		return attachmentContentType;
	}

	public void setAttachmentContentType(String attachmentContentType) {
		this.attachmentContentType = attachmentContentType;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
    
    
}
