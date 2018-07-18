package com.tabor.notes.model;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public final class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "title")
    private final String title;
    @OneToOne
    private final User owner;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final Set<Note> notes = new HashSet<>();
    @ManyToMany(mappedBy = "projects", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final Set<User> participants = new HashSet<>();

    private Project() {
        this.owner = null;
        this.title = null;
    }

    private Project(String title, User owner) {
        this.title = title;
        this.owner = owner;
    }

    public static Project of(String title, User owner) {
        return new Project(title, owner);
    }

    public String getTitle() {
        return title;
    }

    public User getOwner() {
        return owner;
    }

    public Set<Note> getNotes() {
        return Collections.unmodifiableSet(notes);
    }

    void addParticipant(User user) {
        this.participants.add(user);
    }

    public void addNote(Note note) {
        this.notes.add(note);
    }

    public Set<User> getParticipants() {
        return Collections.unmodifiableSet(participants);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id) &&
                Objects.equals(title, project.title) &&
                Objects.equals(owner, project.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, owner);
    }
}
