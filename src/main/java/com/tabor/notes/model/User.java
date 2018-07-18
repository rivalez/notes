package com.tabor.notes.model;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
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
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_project",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id")}
    )
    private final Set<Project> projects = new HashSet<>();

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                role == user.role &&
                Objects.equals(notes, user.notes) &&
                Objects.equals(projects, user.projects);
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
