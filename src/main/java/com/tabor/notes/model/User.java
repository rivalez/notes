package com.tabor.notes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public final class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username")
    private final String username;
    @Column(name = "role")
    private final Role role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final Set<Note> notes = new HashSet<>();
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_project",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id")}
    )
    private final Set<Project> projects = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final Set<ActivationProject> activationProjects = new HashSet<>();

    private User() {
        this.username = null;
        this.role = Role.ADMIN;
    }

    private User(String username, Role role) {
        this.username = username;
        this.role = role;
    }

    public static User of(String username, Role role) {
        return new User(username, role);
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }

    public Set<Note> getNotes() {
        return Collections.unmodifiableSet(notes);
    }

    public Set<Project> getProjects() {
        return Collections.unmodifiableSet(projects);
    }

    public void addProject(Project project) {
        project.addParticipant(this);
        projects.add(project);
    }

    public Set<ActivationProject> getActivationProjects() {
        return activationProjects;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) &&
                role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role=" + role +
                '}';
    }
}
