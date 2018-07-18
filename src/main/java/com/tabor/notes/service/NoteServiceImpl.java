package com.tabor.notes.service;

import com.tabor.notes.model.Note;
import com.tabor.notes.model.Project;
import com.tabor.notes.model.User;
import com.tabor.notes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class NoteServiceImpl implements NoteService {
    private NoteRepository repository;

    @Autowired
    public NoteServiceImpl(NoteRepository repository) {
        this.repository = repository;
    }

    @Override
    public Note findById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<Note> findByUser(User user) {
        return repository.findByUser(user);
    }

    @Override
    public List<Note> findByProject(Project project) {
        return repository.findByProject(project);
    }

    @Override
    public void addNote(String title, String content, Project project, User user) {
        final Note note = Note.of(title, content, project, user);
        repository.save(note);
    }
}
