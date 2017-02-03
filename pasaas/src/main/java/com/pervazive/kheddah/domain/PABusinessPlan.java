package com.pervazive.kheddah.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.pervazive.kheddah.domain.enumeration.PlanType;

import com.pervazive.kheddah.domain.enumeration.PAStatus;

/**
 * A PABusinessPlan.
 */
@Entity
@Table(name = "pa_businessplan")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PABusinessPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "businessplan")
    private PlanType businessplan;

    @Column(name = "users")
    private Integer users;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "pastatus")
    private PAStatus pastatus;

    @Column(name = "projects")
    private Integer projects;

    @OneToMany(mappedBy = "pabporg")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PAOrganization> paorgbps = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlanType getBusinessplan() {
        return businessplan;
    }

    public PABusinessPlan businessplan(PlanType businessplan) {
        this.businessplan = businessplan;
        return this;
    }

    public void setBusinessplan(PlanType businessplan) {
        this.businessplan = businessplan;
    }

    public Integer getUsers() {
        return users;
    }

    public PABusinessPlan users(Integer users) {
        this.users = users;
        return this;
    }

    public void setUsers(Integer users) {
        this.users = users;
    }

    public String getDescription() {
        return description;
    }

    public PABusinessPlan description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PAStatus getPastatus() {
        return pastatus;
    }

    public PABusinessPlan pastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
        return this;
    }

    public void setPastatus(PAStatus pastatus) {
        this.pastatus = pastatus;
    }

    public Integer getProjects() {
        return projects;
    }

    public PABusinessPlan projects(Integer projects) {
        this.projects = projects;
        return this;
    }

    public void setProjects(Integer projects) {
        this.projects = projects;
    }

    public Set<PAOrganization> getPaorgbps() {
        return paorgbps;
    }

    public PABusinessPlan paorgbps(Set<PAOrganization> pAOrganizations) {
        this.paorgbps = pAOrganizations;
        return this;
    }

    public PABusinessPlan addPaorgbp(PAOrganization pAOrganization) {
        paorgbps.add(pAOrganization);
        pAOrganization.setPabporg(this);
        return this;
    }

    public PABusinessPlan removePaorgbp(PAOrganization pAOrganization) {
        paorgbps.remove(pAOrganization);
        pAOrganization.setPabporg(null);
        return this;
    }

    public void setPaorgbps(Set<PAOrganization> pAOrganizations) {
        this.paorgbps = pAOrganizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PABusinessPlan pABusinessPlan = (PABusinessPlan) o;
        if (pABusinessPlan.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pABusinessPlan.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PABusinessPlan{" +
            "id=" + id +
            ", businessplan='" + businessplan + "'" +
            ", users='" + users + "'" +
            ", description='" + description + "'" +
            ", pastatus='" + pastatus + "'" +
            ", projects='" + projects + "'" +
            '}';
    }
}
