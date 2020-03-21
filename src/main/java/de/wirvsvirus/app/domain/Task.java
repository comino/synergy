package de.wirvsvirus.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Task.
 */
@Entity
@Table(name = "task")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "task_skill",
               joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id"))
    private Set<Skill> skills = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("tasks")
    private UserProfile userProfile;

    @ManyToMany(mappedBy = "tasks")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Project> projects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Task name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Task description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public Task skills(Set<Skill> skills) {
        this.skills = skills;
        return this;
    }

    public Task addSkill(Skill skill) {
        this.skills.add(skill);
        skill.getTasks().add(this);
        return this;
    }

    public Task removeSkill(Skill skill) {
        this.skills.remove(skill);
        skill.getTasks().remove(this);
        return this;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public Task userProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
        return this;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public Task projects(Set<Project> projects) {
        this.projects = projects;
        return this;
    }

    public Task addProject(Project project) {
        this.projects.add(project);
        project.getTasks().add(this);
        return this;
    }

    public Task removeProject(Project project) {
        this.projects.remove(project);
        project.getTasks().remove(this);
        return this;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        return id != null && id.equals(((Task) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
