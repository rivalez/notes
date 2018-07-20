package com.tabor.notes.model;

import javax.persistence.*;

@Entity
public final class ProjectInvitation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "projectInvitation_id")
    private final User user;
    private final String activationToken;

    @OneToOne
    private final Project project;

    private ProjectInvitation() {
        this.project = null;
        this.activationToken = null;
        this.user = null;
    }

    private ProjectInvitation(User user, String activationToken, Project project) {
        this.user = user;
        this.activationToken = activationToken;
        this.project = project;
    }

    public static ProjectInvitation of(User user, String activationLink, Project project) {
        return new ProjectInvitation(user, activationLink, project);
    }

    public User getUser() {
        return user;
    }

    public String getActivationToken() {
        return activationToken;
    }
}
