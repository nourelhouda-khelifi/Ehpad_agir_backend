package com.example.Ehpad.repository;

import com.example.Ehpad.entity.PatientNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientNoteRepository extends JpaRepository<PatientNote, Long> {
    
    List<PatientNote> findByPatientId(Long patientId);
    
    List<PatientNote> findByPatientIdOrderByDateNoteDesc(Long patientId);
}
