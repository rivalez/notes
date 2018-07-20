package com.tabor.notes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SharingProject {
    private String sender;
    private String receiver;
    private String projectTitle;

    public SharingProject() {
    }

    public SharingProject(String sender, String receiver, String projectTitle) {
        this.sender = sender;
        this.receiver = receiver;
        this.projectTitle = projectTitle;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getProjectTitle() {
        return projectTitle;
    }
}
