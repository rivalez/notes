package com.tabor.notes.repository;

import com.tabor.notes.model.Project;
import com.tabor.notes.model.Role;
import com.tabor.notes.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RepositoryTestIT {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    public void whenFindByUsername_thenReturnUserWithCorrectDependencies() {
        //given
        final String username = "Marek";
        final User user = User.of(username, Role.ADMIN, "email@email.com");
        final Project project = Project.of("title", user);
        user.addProject(project);
        entityManager.persist(project);
        entityManager.persist(user);
        //when
        final User found = userRepository.findByUsername(username);
        final List<Project> projects = projectRepository.findByOwner(user);
        //then
        assertThat(found).isNotNull();
        assertThat(projects).hasAtLeastOneElementOfType(Project.class);
        assertThat(projects.get(0).getParticipants()).hasAtLeastOneElementOfType(User.class);
        assertThat(found.getProjects()).hasAtLeastOneElementOfType(Project.class);
    }
}