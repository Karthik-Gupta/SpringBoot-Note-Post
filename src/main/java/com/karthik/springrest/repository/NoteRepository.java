package com.karthik.springrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.karthik.springrest.model.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    
}