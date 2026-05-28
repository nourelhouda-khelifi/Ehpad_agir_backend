package com.example.Ehpad.controller;

import java.util.List;

import com.example.Ehpad.dto.PatientNoteCreateDTO;
import com.example.Ehpad.dto.PatientNoteDTO;
import com.example.Ehpad.service.PatientNoteService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/notes")
@Slf4j
public class PatientNoteController {
    private final PatientNoteService patientNoteService;
    
    public PatientNoteController(PatientNoteService patientNoteService) {
        this.patientNoteService = patientNoteService;
    }
    
    @GetMapping
    public ResponseEntity<List<PatientNoteDTO>> getAllNotes() {
        log.info("GET /api/notes");
        return ResponseEntity.ok(patientNoteService.getAllNotes());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PatientNoteDTO> getNoteById(@PathVariable Long id) {
        log.info("GET /api/notes/{}", id);
        return ResponseEntity.ok(patientNoteService.getNoteById(id));
    }
    
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<PatientNoteDTO>> getNotesByPatient(@PathVariable Long patientId) {
        log.info("GET /api/notes/patient/{}", patientId);
        return ResponseEntity.ok(patientNoteService.getNotesByPatientIdOrderByDateDesc(patientId));
    }
    
    @PostMapping("/patient/{patientId}")
    public ResponseEntity<PatientNoteDTO> createNote(@PathVariable Long patientId, @Valid @RequestBody PatientNoteCreateDTO patientNoteDTO) {
        log.info("POST /api/notes/patient/{} - Creating note", patientId);
        PatientNoteDTO createdNote = patientNoteService.createNote(patientId, patientNoteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PatientNoteDTO> updateNote(@PathVariable Long id, @Valid @RequestBody PatientNoteDTO patientNoteDTO) {
        log.info("PUT /api/notes/{} - Updating note", id);
        return ResponseEntity.ok(patientNoteService.updateNote(id, patientNoteDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        log.info("DELETE /api/notes/{}", id);
        patientNoteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}
