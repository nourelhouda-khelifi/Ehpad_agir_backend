package com.example.Ehpad.repository;

import com.example.Ehpad.entity.PatientAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientAlertRepository extends JpaRepository<PatientAlert, Long> {
    
    List<PatientAlert> findByPatientId(Long patientId);
    
    List<PatientAlert> findByAideSoignantId(Long aideSoignantId);
    
    List<PatientAlert> findByResolueFalse();
    
    List<PatientAlert> findByResolueTrue();
}
