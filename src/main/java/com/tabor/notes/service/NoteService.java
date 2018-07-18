package com.tabor.notes.service;

import com.tabor.notes.model.Note;
import com.tabor.notes.model.Project;
import com.tabor.notes.model.User;

import java.util.List;

public interface NoteService {
    Note findById(long id);

    List<Note> findByUser(User user);

    List<Note> findByProject(Project project);

    void addNote(String title, String content, Project project, User user);
}
