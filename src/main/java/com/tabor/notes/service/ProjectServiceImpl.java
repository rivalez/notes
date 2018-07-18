package com.tabor.notes.service;

import com.tabor.notes.model.Project;
import com.tabor.notes.model.User;
import com.tabor.notes.repository.ProjectRepository;
import com.tabor.notes.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void save(String projectTitle, String username) {
        final User user = userRepository.findByUsername(username);
        final Project project = Project.of(projectTitle, user);
        projectRepository.save(project);
        user.addProject(project);
        userRepository.save(user);
    }

    @Override
    public List<Project> findByOwner(User owner) {
        return projectRepository.findByOwner(owner);
    }

    @Override
    public List<Project> findByTitle(String title) {
        return projectRepository.findByTitle(title);
    }

    @Override
    public Project findByTitleAndOwner(String title, String owner) {
        final User user = userRepository.findByUsername(owner);
        return projectRepository.findByTitleAndOwner(title, user);
    }
}
