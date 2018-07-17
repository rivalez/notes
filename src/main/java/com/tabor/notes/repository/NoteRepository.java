package com.tabor.notes.repository;

import com.tabor.notes.model.Note;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {
    Note findById(long id);
}
