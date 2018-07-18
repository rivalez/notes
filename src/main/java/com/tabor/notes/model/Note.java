package com.tabor.notes.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public final class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "title")
    private final String title;
    @Column(name = "content")
    private final String content;
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private final Project project;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private final User user;

    private Note() {
        title = null;
        content = null;
        project = null;
        user = null;
    }

    private Note(String title, String content, Project project, User user) {
        this.title = title;
        this.content = content;
        this.project = project;
        this.user = user;
    }

    public static Note of(String title, String content, Project project, User user) {
        return new Note(title, content, project, user);
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Project getProject() {
        return project;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(title, note.title) &&
                Objects.equals(content, note.content) &&
                Objects.equals(project, note.project) &&
                Objects.equals(user, note.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content, project, user);
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
