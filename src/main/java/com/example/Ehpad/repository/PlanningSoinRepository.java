package com.example.Ehpad.repository;

import com.example.Ehpad.entity.PlanningSoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PlanningSoinRepository extends JpaRepository<PlanningSoin, Long> {
    
    List<PlanningSoin> findByPatientId(Long patientId);
    
    List<PlanningSoin> findByAideSoignantId(Long aideSoignantId);
    
    List<PlanningSoin> findByDatePrevue(LocalDate datePrevue);
    
    List<PlanningSoin> findByPatientIdAndDatePrevue(Long patientId, LocalDate datePrevue);
}
