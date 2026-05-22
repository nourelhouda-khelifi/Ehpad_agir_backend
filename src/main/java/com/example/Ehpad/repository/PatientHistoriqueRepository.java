package com.example.Ehpad.repository;

import com.example.Ehpad.entity.PatientHistorique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientHistoriqueRepository extends JpaRepository<PatientHistorique, Long> {
    
    List<PatientHistorique> findByPatientId(Long patientId);
    
    List<PatientHistorique> findByPatientIdOrderByDateActionDesc(Long patientId);
}
