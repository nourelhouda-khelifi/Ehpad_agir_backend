package com.example.Ehpad.service;

import java.util.List;

import com.example.Ehpad.dto.PatientNoteDTO;
import com.example.Ehpad.entity.Patient;
import com.example.Ehpad.entity.PatientNote;
import com.example.Ehpad.mapper.PatientNoteMapper;
import com.example.Ehpad.repository.PatientNoteRepository;
import com.example.Ehpad.repository.PatientRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class PatientNoteService {
    private final PatientNoteRepository patientNoteRepository;
    private final PatientNoteMapper patientNoteMapper;
    private final PatientRepository patientRepository;
    
    public PatientNoteService(PatientNoteRepository patientNoteRepository, PatientNoteMapper patientNoteMapper,
            PatientRepository patientRepository) {
        this.patientNoteRepository = patientNoteRepository;
        this.patientNoteMapper = patientNoteMapper;
        this.patientRepository = patientRepository;
    }
    
    public List<PatientNoteDTO> getAllNotes() {
        log.info("Fetching all patient notes");
        return patientNoteMapper.toDTOList(patientNoteRepository.findAll());
    }
    
    public PatientNoteDTO getNoteById(Long id) {
        log.info("Fetching note with id: {}", id);
        PatientNote patientNote = patientNoteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note not found with id: " + id));
        return patientNoteMapper.toDTO(patientNote);
    }
    
    public List<PatientNoteDTO> getNotesByPatientId(Long patientId) {
        log.info("Fetching notes for patient: {}", patientId);
        return patientNoteMapper.toDTOList(patientNoteRepository.findByPatientId(patientId));
    }
    
    public List<PatientNoteDTO> getNotesByPatientIdOrderByDateDesc(Long patientId) {
        log.info("Fetching notes for patient: {} ordered by date", patientId);
        return patientNoteMapper.toDTOList(patientNoteRepository.findByPatientIdOrderByDateNoteDesc(patientId));
    }
    
    public PatientNoteDTO createNote(Long patientId, PatientNoteDTO patientNoteDTO) {
        log.info("Creating note for patient: {}", patientId);
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + patientId));
        
        PatientNote patientNote = patientNoteMapper.toEntity(patientNoteDTO);
        patientNote.setPatient(patient);
        PatientNote savedNote = patientNoteRepository.save(patientNote);
        return patientNoteMapper.toDTO(savedNote);
    }
    
    public PatientNoteDTO updateNote(Long id, PatientNoteDTO patientNoteDTO) {
        log.info("Updating note with id: {}", id);
        PatientNote patientNote = patientNoteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note not found with id: " + id));
        
        patientNote.setContenu(patientNoteDTO.getContenu());
        patientNote.setAuteurId(patientNoteDTO.getAuteurId());
        patientNote.setDateNote(patientNoteDTO.getDateNote());
        patientNote.setImportant(patientNoteDTO.getImportant());
        patientNote.setCategorieNote(patientNoteDTO.getCategorieNote());
        
        PatientNote updatedNote = patientNoteRepository.save(patientNote);
        return patientNoteMapper.toDTO(updatedNote);
    }
    
    public void deleteNote(Long id) {
        log.info("Deleting note with id: {}", id);
        if (!patientNoteRepository.existsById(id)) {
            throw new EntityNotFoundException("Note not found with id: " + id);
        }
        patientNoteRepository.deleteById(id);
    }
}
