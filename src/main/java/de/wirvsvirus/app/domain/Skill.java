package de.wirvsvirus.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Skill.
 */
@Entity
@Table(name = "skill")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Skill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "skills")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<UserProfile> userProfiles = new HashSet<>();

    @ManyToMany(mappedBy = "skills")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Project> projects = new HashSet<>();

    @ManyToMany(mappedBy = "skills")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Task> tasks = new HashSet<>();

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

    public Skill name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserProfile> getUserProfiles() {
        return userProfiles;
    }

    public Skill userProfiles(Set<UserProfile> userProfiles) {
        this.userProfiles = userProfiles;
        return this;
    }

    public Skill addUserProfile(UserProfile userProfile) {
        this.userProfiles.add(userProfile);
        userProfile.getSkills().add(this);
        return this;
    }

    public Skill removeUserProfile(UserProfile userProfile) {
        this.userProfiles.remove(userProfile);
        userProfile.getSkills().remove(this);
        return this;
    }

    public void setUserProfiles(Set<UserProfile> userProfiles) {
        this.userProfiles = userProfiles;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public Skill projects(Set<Project> projects) {
        this.projects = projects;
        return this;
    }

    public Skill addProject(Project project) {
        this.projects.add(project);
        project.getSkills().add(this);
        return this;
    }

    public Skill removeProject(Project project) {
        this.projects.remove(project);
        project.getSkills().remove(this);
        return this;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public Skill tasks(Set<Task> tasks) {
        this.tasks = tasks;
        return this;
    }

    public Skill addTask(Task task) {
        this.tasks.add(task);
        task.getSkills().add(this);
        return this;
    }

    public Skill removeTask(Task task) {
        this.tasks.remove(task);
        task.getSkills().remove(this);
        return this;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Skill)) {
            return false;
        }
        return id != null && id.equals(((Skill) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Skill{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
