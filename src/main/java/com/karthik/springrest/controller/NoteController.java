
package com.karthik.springrest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.karthik.springrest.exception.ResourceNotFoundException;
import com.karthik.springrest.model.Note;
import com.karthik.springrest.repository.NoteRepository;

@RestController
@RequestMapping("/api")
public class NoteController {
    @Autowired
    NoteRepository noteRepository;

    // Get all Notes
    @GetMapping("/notes")
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    // Create a new Note
    /*
     * The @RequestBody annotation is used to bind the request body with a method parameter. The @Valid annotation makes sure that the request body is valid. Remember, we had
     * marked Note’s title and content with @NotBlank annotation in the Note model? If the request body doesn’t have a title or a content, then spring will return a 400 BadRequest
     * error to the client.
     */
    @PostMapping("/notes")
    public Note createNode(@Valid @RequestBody Note note) {
        return noteRepository.save(note);
    }

    // Get single Note
    /*
     * The @PathVariable annotation, as the name suggests, is used to bind a path variable with a method parameter. In the above method, we are throwing a ResourceNotFoundException
     * whenever a Note with the given id is not found. This will cause Spring Boot to return a 404 Not Found error to the client (we have added a @ResponseStatus(value =
     * HttpStatus.NOT_FOUND) annotation to the ResourceNotFoundException class)
     */
    @GetMapping("/notes/{id}")
    public Note getNoteById(@PathVariable(name = "id") Long noteId) {
        return noteRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
    }

    // Update Note on id
    @PutMapping("/notes/{id}")
    public Note updateNote(@PathVariable(name = "id") Long noteId, @Valid @RequestBody Note noteRequest) {
        /*
         * Note note = noteRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId)); note.setTitle(noteRequest.getTitle());
         * note.setContent(noteRequest.getContent()); return noteRepository.save(note);
         */

        return noteRepository.findById(noteId).map(note -> {
            note.setTitle(noteRequest.getTitle());
            note.setContent(noteRequest.getContent());
            return noteRepository.save(note);
        }).orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
    }

    // Delete a Note
    @DeleteMapping("/notes/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(name = "id") Long noteId) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

        noteRepository.delete(note);

        return ResponseEntity.ok().build();
    }
}
