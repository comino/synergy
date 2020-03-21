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
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

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

    public String getName() {
        return name;
    }

    public Idea name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
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
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
