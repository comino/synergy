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
            "}";
    }
}
