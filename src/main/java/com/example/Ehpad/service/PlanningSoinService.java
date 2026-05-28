package com.example.Ehpad.service;

import java.time.LocalDate;
import java.util.List;

import com.example.Ehpad.dto.PlanningSoinCreateDTO;
import com.example.Ehpad.dto.PlanningSoinDTO;
import com.example.Ehpad.entity.AideSoignant;
import com.example.Ehpad.entity.Patient;
import com.example.Ehpad.entity.PlanningSoin;
import com.example.Ehpad.entity.PlanningStatut;
import com.example.Ehpad.entity.TypeSoin;
import com.example.Ehpad.mapper.PlanningSoinMapper;
import com.example.Ehpad.repository.AideSoignantRepository;
import com.example.Ehpad.repository.PatientRepository;
import com.example.Ehpad.repository.PlanningSoinRepository;
import com.example.Ehpad.repository.TypeSoinRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class PlanningSoinService {
    private final PlanningSoinRepository planningSoinRepository;
    private final PlanningSoinMapper planningSoinMapper;
    private final PatientRepository patientRepository;
    private final TypeSoinRepository typeSoinRepository;
    private final AideSoignantRepository aideSoignantRepository;
    
    public PlanningSoinService(PlanningSoinRepository planningSoinRepository, PlanningSoinMapper planningSoinMapper,
            PatientRepository patientRepository, TypeSoinRepository typeSoinRepository,
            AideSoignantRepository aideSoignantRepository) {
        this.planningSoinRepository = planningSoinRepository;
        this.planningSoinMapper = planningSoinMapper;
        this.patientRepository = patientRepository;
        this.typeSoinRepository = typeSoinRepository;
        this.aideSoignantRepository = aideSoignantRepository;
    }
    
    public List<PlanningSoinDTO> getAllPlanningsSoins() {
        log.info("Fetching all care planning");
        return planningSoinMapper.toDTOList(planningSoinRepository.findAll());
    }
    
    public PlanningSoinDTO getPlanningSoinById(Long id) {
        log.info("Fetching care planning with id: {}", id);
        PlanningSoin planningSoin = planningSoinRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Care planning not found with id: " + id));
        return planningSoinMapper.toDTO(planningSoin);
    }
    
    public PlanningSoinDTO createPlanningSoin(PlanningSoinCreateDTO planningSoinDTO) {
        log.info("Creating care planning for patient: {}", planningSoinDTO.getPatientId());
        
        Patient patient = patientRepository.findById(planningSoinDTO.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));
        TypeSoin typeSoin = typeSoinRepository.findById(planningSoinDTO.getTypeSoinId())
                .orElseThrow(() -> new EntityNotFoundException("Care type not found"));
        
        PlanningSoin planningSoin = planningSoinMapper.toEntity(planningSoinDTO);
        planningSoin.setPatient(patient);
        planningSoin.setTypeSoin(typeSoin);
        planningSoin.setStatut(planningSoinDTO.getStatut() != null ? planningSoinDTO.getStatut() : PlanningStatut.PLANIFIE);
        
        if (planningSoinDTO.getAideSoignantId() != null) {
            AideSoignant aideSoignant = aideSoignantRepository.findById(planningSoinDTO.getAideSoignantId())
                    .orElseThrow(() -> new EntityNotFoundException("Care staff not found"));
            planningSoin.setAideSoignant(aideSoignant);
        }
        
        PlanningSoin savedPlanningSoin = planningSoinRepository.save(planningSoin);
        return planningSoinMapper.toDTO(savedPlanningSoin);
    }
    
    public PlanningSoinDTO updatePlanningSoin(Long id, PlanningSoinDTO planningSoinDTO) {
        log.info("Updating care planning with id: {}", id);
        PlanningSoin planningSoin = planningSoinRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Care planning not found with id: " + id));
        
        if (planningSoinDTO.getDatePrevue() != null) {
            planningSoin.setDatePrevue(planningSoinDTO.getDatePrevue());
        }
        if (planningSoinDTO.getHeurePrevue() != null) {
            planningSoin.setHeurePrevue(planningSoinDTO.getHeurePrevue());
        }
        if (planningSoinDTO.getDureePrevue() != null) {
            planningSoin.setDureePrevue(planningSoinDTO.getDureePrevue());
        }
        if (planningSoinDTO.getMoment() != null) {
            planningSoin.setMoment(planningSoinDTO.getMoment());
        }
        if (planningSoinDTO.getStatut() != null) {
            planningSoin.setStatut(planningSoinDTO.getStatut());
        }
        if (planningSoinDTO.getCommentaire() != null) {
            planningSoin.setCommentaire(planningSoinDTO.getCommentaire());
        }
        if (planningSoinDTO.getAideSoignantId() != null) {
            AideSoignant aideSoignant = aideSoignantRepository.findById(planningSoinDTO.getAideSoignantId())
                    .orElseThrow(() -> new EntityNotFoundException("Care staff not found"));
            planningSoin.setAideSoignant(aideSoignant);
        }
        
        PlanningSoin updatedPlanningSoin = planningSoinRepository.save(planningSoin);
        return planningSoinMapper.toDTO(updatedPlanningSoin);
    }
    
    public void deletePlanningSoin(Long id) {
        log.info("Deleting care planning with id: {}", id);
        if (!planningSoinRepository.existsById(id)) {
            throw new EntityNotFoundException("Care planning not found with id: " + id);
        }
        planningSoinRepository.deleteById(id);
    }
    
    public List<PlanningSoinDTO> getPlanningsByPatientId(Long patientId) {
        log.info("Fetching care planning for patient: {}", patientId);
        return planningSoinMapper.toDTOList(planningSoinRepository.findByPatientId(patientId));
    }
    
    public List<PlanningSoinDTO> getPlanningsByAideSoignantId(Long aideSoignantId) {
        log.info("Fetching care planning for staff member: {}", aideSoignantId);
        return planningSoinMapper.toDTOList(planningSoinRepository.findByAideSoignantId(aideSoignantId));
    }
    
    public List<PlanningSoinDTO> getPlanningsByDate(LocalDate date) {
        log.info("Fetching care planning for date: {}", date);
        return planningSoinMapper.toDTOList(planningSoinRepository.findByDatePrevue(date));
    }
    
    public List<PlanningSoinDTO> getPlanningsByPatientAndDate(Long patientId, LocalDate date) {
        log.info("Fetching care planning for patient: {} on date: {}", patientId, date);
        return planningSoinMapper.toDTOList(planningSoinRepository.findByPatientIdAndDatePrevue(patientId, date));
    }
}
