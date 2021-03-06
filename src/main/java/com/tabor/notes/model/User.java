package com.tabor.notes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(schema = "public")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username")
    @NotEmpty
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
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private final Set<ProjectInvitation> projectInvitations = new HashSet<>();
    @Email
    private final String email;

    private User() {
        this.username = null;
        this.email = null;
        this.role = Role.ADMIN;
    }

    private User(String username, Role role, String email) {
        this.username = username;
        this.role = role;
        this.email = email;
    }

    public static User of(String username, Role role, String email) {
        return new User(username, role, email);
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

    public Set<ProjectInvitation> getProjectInvitations() {
        return Collections.unmodifiableSet(projectInvitations);
    }

    public void removeProjectInvitation(ProjectInvitation invitation) {
        projectInvitations.remove(invitation);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
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
        return String.format("User{id=%d, username='%s', role=%s, email='%s'}", id, username, role, email);
    }
}
