package com.tabor.notes.service;

import com.tabor.notes.model.Note;
import com.tabor.notes.model.Project;
import com.tabor.notes.model.User;
import com.tabor.notes.repository.NoteRepository;
import com.tabor.notes.repository.ProjectRepository;
import com.tabor.notes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository repository, UserRepository userRepository, ProjectRepository projectRepository) {
        this.noteRepository = repository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public Note findById(long id) {
        return noteRepository.findById(id);
    }

    @Override
    public List<Note> findByUser(User user) {
        return noteRepository.findByUser(user);
    }

    @Override
    public List<Note> findByProject(Project project) {
        return noteRepository.findByProject(project);
    }

    @Override
    public void addNote(String title, String content, String projectTitle, String username) {
        final User user = userRepository.findByUsername(username);
        final Project project = projectRepository.findByTitleAndOwner(projectTitle, user);
        final Note note = Note.of(title, content, project, user);

        noteRepository.save(note);
    }
}
