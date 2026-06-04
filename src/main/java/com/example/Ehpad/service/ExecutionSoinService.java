package com.example.Ehpad.service;

import java.time.LocalDate;
import java.util.List;

import com.example.Ehpad.dto.ExecutionSoinCreateDTO;
import com.example.Ehpad.dto.ExecutionSoinDTO;
import com.example.Ehpad.entity.AideSoignant;
import com.example.Ehpad.entity.ExecutionSoin;
import com.example.Ehpad.entity.Patient;
import com.example.Ehpad.entity.PlanningSoin;
import com.example.Ehpad.entity.PlanningStatut;
import com.example.Ehpad.entity.TypeSoin;
import com.example.Ehpad.mapper.ExecutionSoinMapper;
import com.example.Ehpad.repository.AideSoignantRepository;
import com.example.Ehpad.repository.ExecutionSoinRepository;
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
public class ExecutionSoinService {
    private final ExecutionSoinRepository executionSoinRepository;
    private final ExecutionSoinMapper executionSoinMapper;
    private final PatientRepository patientRepository;
    private final TypeSoinRepository typeSoinRepository;
    private final AideSoignantRepository aideSoignantRepository;
    private final PlanningSoinRepository planningSoinRepository;

    public ExecutionSoinService(ExecutionSoinRepository executionSoinRepository,
            ExecutionSoinMapper executionSoinMapper,
            PatientRepository patientRepository, TypeSoinRepository typeSoinRepository,
            AideSoignantRepository aideSoignantRepository, PlanningSoinRepository planningSoinRepository) {
        this.executionSoinRepository = executionSoinRepository;
        this.executionSoinMapper = executionSoinMapper;
        this.patientRepository = patientRepository;
        this.typeSoinRepository = typeSoinRepository;
        this.aideSoignantRepository = aideSoignantRepository;
        this.planningSoinRepository = planningSoinRepository;
    }

    public List<ExecutionSoinDTO> getAllExecutionsSoins() {
        log.info("Fetching all care executions");
        return executionSoinMapper.toDTOList(executionSoinRepository.findAll());
    }

    public ExecutionSoinDTO getExecutionSoinById(Long id) {
        log.info("Fetching care execution with id: {}", id);
        ExecutionSoin executionSoin = executionSoinRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Care execution not found with id: " + id));
        return executionSoinMapper.toDTO(executionSoin);
    }

    public ExecutionSoinDTO createExecutionSoin(ExecutionSoinCreateDTO executionSoinDTO) {
        log.info("Creating care execution for patient: {}", executionSoinDTO.getPatientId());

        Patient patient = patientRepository.findById(executionSoinDTO.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));
        TypeSoin typeSoin = typeSoinRepository.findById(executionSoinDTO.getTypeSoinId())
                .orElseThrow(() -> new EntityNotFoundException("Care type not found"));

        ExecutionSoin executionSoin = executionSoinMapper.toEntity(executionSoinDTO);
        executionSoin.setPatient(patient);
        executionSoin.setTypeSoin(typeSoin);
        executionSoin.setStatut(PlanningStatut.EFFECTUE);

        if (executionSoinDTO.getAideSoignantId() != null) {
            AideSoignant aideSoignant = aideSoignantRepository.findById(executionSoinDTO.getAideSoignantId())
                    .orElseThrow(() -> new EntityNotFoundException("Care staff not found"));
            executionSoin.setAideSoignant(aideSoignant);
        }

        if (executionSoinDTO.getPlanningSoinId() != null) {
            PlanningSoin planningSoin = planningSoinRepository.findById(executionSoinDTO.getPlanningSoinId())
                    .orElseThrow(() -> new EntityNotFoundException("Care planning not found"));
            executionSoin.setPlanningSoin(planningSoin);
        }

        // Le frontend envoie un POST séparé pour chaque aide-soignant sur les soins partagés.
        // Ne pas créer de 2e exécution ici pour éviter les doublons.
        return executionSoinMapper.toDTO(executionSoinRepository.save(executionSoin));
    }

    public ExecutionSoinDTO updateExecutionSoin(Long id, ExecutionSoinDTO executionSoinDTO) {
        log.info("Updating care execution with id: {}", id);
        ExecutionSoin executionSoin = executionSoinRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Care execution not found with id: " + id));

        if (executionSoinDTO.getDateExecution() != null) {
            executionSoin.setDateExecution(executionSoinDTO.getDateExecution());
        }
        if (executionSoinDTO.getHeureExecution() != null) {
            executionSoin.setHeureExecution(executionSoinDTO.getHeureExecution());
        }
        if (executionSoinDTO.getStatut() != null) {
            executionSoin.setStatut(executionSoinDTO.getStatut());
        }
        if (executionSoinDTO.getCommentaire() != null) {
            executionSoin.setCommentaire(executionSoinDTO.getCommentaire());
        }

        ExecutionSoin updatedExecutionSoin = executionSoinRepository.save(executionSoin);
        return executionSoinMapper.toDTO(updatedExecutionSoin);
    }

    public void deleteExecutionSoin(Long id) {
        log.info("Deleting care execution with id: {}", id);
        if (!executionSoinRepository.existsById(id)) {
            throw new EntityNotFoundException("Care execution not found with id: " + id);
        }
        executionSoinRepository.deleteById(id);
    }

    public List<ExecutionSoinDTO> getExecutionsByPatientId(Long patientId) {
        log.info("Fetching care executions for patient: {}", patientId);
        return executionSoinMapper.toDTOList(executionSoinRepository.findByPatientId(patientId));
    }

    public List<ExecutionSoinDTO> getExecutionsByAideSoignantId(Long aideSoignantId) {
        log.info("Fetching care executions for staff member: {}", aideSoignantId);
        return executionSoinMapper.toDTOList(executionSoinRepository.findByAideSoignantId(aideSoignantId));
    }

    public List<ExecutionSoinDTO> getExecutionsByDate(LocalDate date) {
        log.info("Fetching care executions for date: {}", date);
        return executionSoinMapper.toDTOList(executionSoinRepository.findByDateExecution(date));
    }

    public List<ExecutionSoinDTO> getExecutionsByPatientAndDate(Long patientId, LocalDate date) {
        log.info("Fetching care executions for patient: {} on date: {}", patientId, date);
        return executionSoinMapper.toDTOList(executionSoinRepository.findByPatientIdAndDateExecution(patientId, date));
    }
}
