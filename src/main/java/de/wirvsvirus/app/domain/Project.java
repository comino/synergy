package de.wirvsvirus.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "repository")
    private String repository;

    @Column(name = "slack")
    private String slack;

    @Column(name = "email")
    private String email;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "project_user",
               joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> users = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "project_skill",
               joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id"))
    private Set<Skill> skills = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "project_task",
               joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"))
    private Set<Task> tasks = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("projects")
    private Idea idea;

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

    public Project name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRepository() {
        return repository;
    }

    public Project repository(String repository) {
        this.repository = repository;
        return this;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getSlack() {
        return slack;
    }

    public Project slack(String slack) {
        this.slack = slack;
        return this;
    }

    public void setSlack(String slack) {
        this.slack = slack;
    }

    public String getEmail() {
        return email;
    }

    public Project email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public Project description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public Project image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Project imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Project users(Set<User> users) {
        this.users = users;
        return this;
    }

    public Project addUser(User user) {
        this.users.add(user);
        return this;
    }

    public Project removeUser(User user) {
        this.users.remove(user);
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public Project skills(Set<Skill> skills) {
        this.skills = skills;
        return this;
    }

    public Project addSkill(Skill skill) {
        this.skills.add(skill);
        skill.getProjects().add(this);
        return this;
    }

    public Project removeSkill(Skill skill) {
        this.skills.remove(skill);
        skill.getProjects().remove(this);
        return this;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public Project tasks(Set<Task> tasks) {
        this.tasks = tasks;
        return this;
    }

    public Project addTask(Task task) {
        this.tasks.add(task);
        task.getProjects().add(this);
        return this;
    }

    public Project removeTask(Task task) {
        this.tasks.remove(task);
        task.getProjects().remove(this);
        return this;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Idea getIdea() {
        return idea;
    }

    public Project idea(Idea idea) {
        this.idea = idea;
        return this;
    }

    public void setIdea(Idea idea) {
        this.idea = idea;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        return id != null && id.equals(((Project) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", repository='" + getRepository() + "'" +
            ", slack='" + getSlack() + "'" +
            ", email='" + getEmail() + "'" +
            ", description='" + getDescription() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            "}";
    }
}
