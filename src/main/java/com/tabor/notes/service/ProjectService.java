package com.tabor.notes.service;

import com.tabor.notes.model.Project;
import com.tabor.notes.model.User;

import java.util.List;

public interface ProjectService {
    void save(String projectTitle, String username);

    List<Project> findByOwner(User owner);

    List<Project> findByTitle(String title);

    Project findByTitleAndOwner(String title, String owner);
}
