package com.tabor.notes.repository;

import com.tabor.notes.model.ProjectInvitation;
import com.tabor.notes.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProjectInvitationRepository extends CrudRepository<ProjectInvitation, Long> {
    Set<ProjectInvitation> findByUser(User user);

    ProjectInvitation findByToken(String token);
}
