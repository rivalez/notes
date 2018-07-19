package com.tabor.notes.model;

import javax.persistence.*;

@Entity
final class ActivationProject {
    @ManyToOne
    @JoinColumn(name = "activationProject_id")
    private final User user;
    private final String activationLink;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private ActivationProject() {
        this.activationLink = null;
        this.user = null;
    }

    private ActivationProject(User user, String activationLink) {
        this.user = user;
        this.activationLink = activationLink;
    }

}
