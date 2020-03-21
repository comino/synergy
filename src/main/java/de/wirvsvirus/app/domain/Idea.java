package de.wirvsvirus.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Idea.
 */
@Entity
@Table(name = "idea")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Idea implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "problems", nullable = false)
    private String problems;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "solution")
    private String solution;

    @Column(name = "target_audience")
    private String targetAudience;

    @Column(name = "stake_holder")
    private String stakeHolder;

    @Column(name = "slack_channel")
    private String slackChannel;

    @Column(name = "ministry_project")
    private Boolean ministryProject;

    @OneToMany(mappedBy = "idea")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Project> projects = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("ideas")
    private Challenge challenge;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Idea title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProblems() {
        return problems;
    }

    public Idea problems(String problems) {
        this.problems = problems;
        return this;
    }

    public void setProblems(String problems) {
        this.problems = problems;
    }

    public String getDescription() {
        return description;
    }

    public Idea description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSolution() {
        return solution;
    }

    public Idea solution(String solution) {
        this.solution = solution;
        return this;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public Idea targetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
        return this;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    public String getStakeHolder() {
        return stakeHolder;
    }

    public Idea stakeHolder(String stakeHolder) {
        this.stakeHolder = stakeHolder;
        return this;
    }

    public void setStakeHolder(String stakeHolder) {
        this.stakeHolder = stakeHolder;
    }

    public String getSlackChannel() {
        return slackChannel;
    }

    public Idea slackChannel(String slackChannel) {
        this.slackChannel = slackChannel;
        return this;
    }

    public void setSlackChannel(String slackChannel) {
        this.slackChannel = slackChannel;
    }

    public Boolean isMinistryProject() {
        return ministryProject;
    }

    public Idea ministryProject(Boolean ministryProject) {
        this.ministryProject = ministryProject;
        return this;
    }

    public void setMinistryProject(Boolean ministryProject) {
        this.ministryProject = ministryProject;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public Idea projects(Set<Project> projects) {
        this.projects = projects;
        return this;
    }

    public Idea addProject(Project project) {
        this.projects.add(project);
        project.setIdea(this);
        return this;
    }

    public Idea removeProject(Project project) {
        this.projects.remove(project);
        project.setIdea(null);
        return this;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public Idea challenge(Challenge challenge) {
        this.challenge = challenge;
        return this;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Idea)) {
            return false;
        }
        return id != null && id.equals(((Idea) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Idea{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", problems='" + getProblems() + "'" +
            ", description='" + getDescription() + "'" +
            ", solution='" + getSolution() + "'" +
            ", targetAudience='" + getTargetAudience() + "'" +
            ", stakeHolder='" + getStakeHolder() + "'" +
            ", slackChannel='" + getSlackChannel() + "'" +
            ", ministryProject='" + isMinistryProject() + "'" +
            "}";
    }
}
