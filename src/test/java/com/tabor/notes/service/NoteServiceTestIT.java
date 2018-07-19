package com.tabor.notes.service;

import com.tabor.notes.model.Note;
import com.tabor.notes.model.Project;
import com.tabor.notes.model.Role;
import com.tabor.notes.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteServiceTestIT {

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Test
    public void whenSaveNote_thenUpdateCorrespondingProject() {
        //given
        final String username = "Adam";
        final String projectTitle = "Project title";
        userService.saveUser(username, Role.ADMIN);
        projectService.save(projectTitle, username);
        final User userEntity = userService.findByUsername(username);
        //when
        noteService.addNote("title", "content", projectTitle, username);
        final List<Note> notes = noteService.findByUser(userEntity);
        final Project projectEntity = projectService.findByTitleAndOwner(projectTitle, username);
        final User userAfterNoteAddition = userService.findByUsername(username);
        //then
        assertThat(notes).hasSize(1);
        assertThat(notes.get(0).getProject().getOwner()).isEqualTo(userEntity);
        assertThat(projectEntity.getNotes()).hasSize(1);
        assertThat(userAfterNoteAddition.getNotes()).hasSize(1);
        assertThat(userAfterNoteAddition.getProjects()).hasSize(1);
    }
}
