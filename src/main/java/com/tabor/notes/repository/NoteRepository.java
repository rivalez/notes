package com.tabor.notes.repository;

import com.tabor.notes.model.Note;
import com.tabor.notes.model.Project;
import com.tabor.notes.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {
    Note findById(long id);

    List<Note> findByUser(User user);

    List<Note> findByProject(Project project);
}
