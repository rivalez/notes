package com.tabor.notes.repository;

import com.tabor.notes.model.Project;
import com.tabor.notes.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    List<Project> findByOwner(User user);
    List<Project> findByTitle(String title);
}
