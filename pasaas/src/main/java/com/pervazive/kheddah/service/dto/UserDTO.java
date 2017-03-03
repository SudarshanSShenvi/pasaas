package com.pervazive.kheddah.service.dto;

import com.pervazive.kheddah.config.Constants;

import com.pervazive.kheddah.domain.Authority;
import com.pervazive.kheddah.domain.PAOrganization;
import com.pervazive.kheddah.domain.PAProject;
import com.pervazive.kheddah.domain.User;

import org.hibernate.validator.constraints.Email;
import org.springframework.social.google.api.plus.Organization;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO representing a user, with his authorities, projects and organization
 */
public class UserDTO {

    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 100)
    private String login;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;
    
    @Size(max = 255)
    private String defaultOrganization;

    @Email
    @Size(min = 5, max = 100)
    private String email;

    private boolean activated = false;

    @Size(min = 2, max = 5)
    private String langKey;

    private Set<String> authorities;
    
    private Set<String> organizations;
    
    private Set<String> projects;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this(user.getLogin(), user.getFirstName(), user.getLastName(), user.getDefaultOrganization(),
            user.getEmail(), user.getActivated(), user.getLangKey(),
            user.getAuthorities().stream().map(Authority::getName)
                .collect(Collectors.toSet()), 
                user.getOrganizations().stream().map(PAOrganization::getOrganization)
                .collect(Collectors.toSet()), 
                		user.getProjects().stream().map(PAProject::getProjectname)
                        .collect(Collectors.toSet())
        );
    }

    public UserDTO(String login, String firstName, String lastName, String defaultOrganization,
        String email, boolean activated, String langKey, Set<String> authorities, Set<String> organizations, Set<String> projects) {

        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.defaultOrganization = defaultOrganization;
        this.email = email;
        this.activated = activated;
        this.langKey = langKey;
        this.authorities = authorities;
        this.organizations = organizations;
        this.projects = projects;
    }

    public String getLogin() {
        return login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDefaultOrganization() {
		return defaultOrganization;
	}

	public void setDefaultOrganization(String defaultOrganization) {
		this.defaultOrganization = defaultOrganization;
	}

	public String getEmail() {
        return email;
    }

    public boolean isActivated() {
        return activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }
    
    public Set<String> getOrganizations() {
        return organizations;
    }
    
    public Set<String> getProjects() {
        return projects;
    }
    
    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }
    
    public void setOrganizations(Set<String> organizations) {
        this.organizations= organizations;
    }
    
    public void setProjects(Set<String> projects ) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
            "login='" + login + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", activated=" + activated +
            ", langKey='" + langKey + '\'' +
            ", authorities=" + authorities +
            "}";
    }
}
