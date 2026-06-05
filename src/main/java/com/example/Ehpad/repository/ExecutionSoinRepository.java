package com.example.Ehpad.repository;

import com.example.Ehpad.entity.ExecutionSoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExecutionSoinRepository extends JpaRepository<ExecutionSoin, Long> {
    
    List<ExecutionSoin> findByPatientId(Long patientId);
    
    List<ExecutionSoin> findByAideSoignantId(Long aideSoignantId);
    
    List<ExecutionSoin> findByDateExecution(LocalDate dateExecution);

    List<ExecutionSoin> findByPatientIdAndDateExecution(Long patientId, LocalDate dateExecution);

    List<ExecutionSoin> findByDateExecutionBetween(LocalDate startDate, LocalDate endDate);
}
