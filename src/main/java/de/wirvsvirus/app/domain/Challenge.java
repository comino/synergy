package de.wirvsvirus.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Challenge.
 */
@Entity
@Table(name = "challenge")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Challenge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

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

    @OneToMany(mappedBy = "challenge")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Idea> ideas = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "challenge_category",
               joinColumns = @JoinColumn(name = "challenge_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private Set<Category> categories = new HashSet<>();

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

    public Challenge name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProblems() {
        return problems;
    }

    public Challenge problems(String problems) {
        this.problems = problems;
        return this;
    }

    public void setProblems(String problems) {
        this.problems = problems;
    }

    public String getDescription() {
        return description;
    }

    public Challenge description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSolution() {
        return solution;
    }

    public Challenge solution(String solution) {
        this.solution = solution;
        return this;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public Challenge targetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
        return this;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    public String getStakeHolder() {
        return stakeHolder;
    }

    public Challenge stakeHolder(String stakeHolder) {
        this.stakeHolder = stakeHolder;
        return this;
    }

    public void setStakeHolder(String stakeHolder) {
        this.stakeHolder = stakeHolder;
    }

    public String getSlackChannel() {
        return slackChannel;
    }

    public Challenge slackChannel(String slackChannel) {
        this.slackChannel = slackChannel;
        return this;
    }

    public void setSlackChannel(String slackChannel) {
        this.slackChannel = slackChannel;
    }

    public Boolean isMinistryProject() {
        return ministryProject;
    }

    public Challenge ministryProject(Boolean ministryProject) {
        this.ministryProject = ministryProject;
        return this;
    }

    public void setMinistryProject(Boolean ministryProject) {
        this.ministryProject = ministryProject;
    }

    public Set<Idea> getIdeas() {
        return ideas;
    }

    public Challenge ideas(Set<Idea> ideas) {
        this.ideas = ideas;
        return this;
    }

    public Challenge addIdea(Idea idea) {
        this.ideas.add(idea);
        idea.setChallenge(this);
        return this;
    }

    public Challenge removeIdea(Idea idea) {
        this.ideas.remove(idea);
        idea.setChallenge(null);
        return this;
    }

    public void setIdeas(Set<Idea> ideas) {
        this.ideas = ideas;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Challenge categories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public Challenge addCategory(Category category) {
        this.categories.add(category);
        category.getChallenges().add(this);
        return this;
    }

    public Challenge removeCategory(Category category) {
        this.categories.remove(category);
        category.getChallenges().remove(this);
        return this;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Challenge)) {
            return false;
        }
        return id != null && id.equals(((Challenge) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Challenge{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
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
