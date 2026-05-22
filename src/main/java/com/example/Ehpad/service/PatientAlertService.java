package com.example.Ehpad.service;

import java.util.List;

import com.example.Ehpad.dto.PatientAlertDTO;
import com.example.Ehpad.entity.AideSoignant;
import com.example.Ehpad.entity.Patient;
import com.example.Ehpad.entity.PatientAlert;
import com.example.Ehpad.mapper.PatientAlertMapper;
import com.example.Ehpad.repository.AideSoignantRepository;
import com.example.Ehpad.repository.PatientAlertRepository;
import com.example.Ehpad.repository.PatientRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class PatientAlertService {
    private final PatientAlertRepository patientAlertRepository;
    private final PatientAlertMapper patientAlertMapper;
    private final PatientRepository patientRepository;
    private final AideSoignantRepository aideSoignantRepository;
    
    public PatientAlertService(PatientAlertRepository patientAlertRepository, PatientAlertMapper patientAlertMapper,
            PatientRepository patientRepository, AideSoignantRepository aideSoignantRepository) {
        this.patientAlertRepository = patientAlertRepository;
        this.patientAlertMapper = patientAlertMapper;
        this.patientRepository = patientRepository;
        this.aideSoignantRepository = aideSoignantRepository;
    }
    
    public List<PatientAlertDTO> getAllAlerts() {
        log.info("Fetching all patient alerts");
        return patientAlertMapper.toDTOList(patientAlertRepository.findAll());
    }
    
    public PatientAlertDTO getAlertById(Long id) {
        log.info("Fetching alert with id: {}", id);
        PatientAlert patientAlert = patientAlertRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Alert not found with id: " + id));
        return patientAlertMapper.toDTO(patientAlert);
    }
    
    public List<PatientAlertDTO> getAlertsByPatientId(Long patientId) {
        log.info("Fetching alerts for patient: {}", patientId);
        return patientAlertMapper.toDTOList(patientAlertRepository.findByPatientId(patientId));
    }
    
    public List<PatientAlertDTO> getAlertsByAideSoignantId(Long aideSoignantId) {
        log.info("Fetching alerts for staff member: {}", aideSoignantId);
        return patientAlertMapper.toDTOList(patientAlertRepository.findByAideSoignantId(aideSoignantId));
    }
    
    public List<PatientAlertDTO> getUnresolvedAlerts() {
        log.info("Fetching unresolved alerts");
        return patientAlertMapper.toDTOList(patientAlertRepository.findByResolueFalse());
    }
    
    public List<PatientAlertDTO> getResolvedAlerts() {
        log.info("Fetching resolved alerts");
        return patientAlertMapper.toDTOList(patientAlertRepository.findByResolueTrue());
    }
    
    public PatientAlertDTO createAlert(PatientAlertDTO patientAlertDTO) {
        log.info("Creating alert for patient: {}", patientAlertDTO.getPatientId());
        Patient patient = patientRepository.findById(patientAlertDTO.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));
        
        PatientAlert patientAlert = patientAlertMapper.toEntity(patientAlertDTO);
        patientAlert.setPatient(patient);
        patientAlert.setResolue(false);
        
        if (patientAlertDTO.getAideSoignantId() != null) {
            AideSoignant aideSoignant = aideSoignantRepository.findById(patientAlertDTO.getAideSoignantId())
                    .orElseThrow(() -> new EntityNotFoundException("Care staff not found"));
            patientAlert.setAideSoignant(aideSoignant);
        }
        
        PatientAlert savedAlert = patientAlertRepository.save(patientAlert);
        return patientAlertMapper.toDTO(savedAlert);
    }
    
    public PatientAlertDTO updateAlert(Long id, PatientAlertDTO patientAlertDTO) {
        log.info("Updating alert with id: {}", id);
        PatientAlert patientAlert = patientAlertRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Alert not found with id: " + id));
        
        // Only update fields if they are provided (not null)
        if (patientAlertDTO.getType() != null) {
            patientAlert.setType(patientAlertDTO.getType());
        }
        if (patientAlertDTO.getNiveau() != null) {
            patientAlert.setNiveau(patientAlertDTO.getNiveau());
        }
        if (patientAlertDTO.getMessage() != null) {
            patientAlert.setMessage(patientAlertDTO.getMessage());
        }
        if (patientAlertDTO.getResolue() != null) {
            patientAlert.setResolue(patientAlertDTO.getResolue());
        }
        if (patientAlertDTO.getDateResolution() != null) {
            patientAlert.setDateResolution(patientAlertDTO.getDateResolution());
        }
        
        // Handle patient update if provided
        if (patientAlertDTO.getPatientId() != null && !patientAlertDTO.getPatientId().equals(patientAlert.getPatient().getId())) {
            Patient patient = patientRepository.findById(patientAlertDTO.getPatientId())
                    .orElseThrow(() -> new EntityNotFoundException("Patient not found"));
            patientAlert.setPatient(patient);
        }
        
        // Handle aide-soignant update if provided
        if (patientAlertDTO.getAideSoignantId() != null) {
            AideSoignant aideSoignant = aideSoignantRepository.findById(patientAlertDTO.getAideSoignantId())
                    .orElseThrow(() -> new EntityNotFoundException("Care staff not found"));
            patientAlert.setAideSoignant(aideSoignant);
        } else if (patientAlertDTO.getAideSoignantId() == null && patientAlertDTO.toString().contains("aideSoignantId")) {
            // Only clear if explicitly set to null
            patientAlert.setAideSoignant(null);
        }
        
        PatientAlert updatedAlert = patientAlertRepository.save(patientAlert);
        return patientAlertMapper.toDTO(updatedAlert);
    }
    
    public void deleteAlert(Long id) {
        log.info("Deleting alert with id: {}", id);
        if (!patientAlertRepository.existsById(id)) {
            throw new EntityNotFoundException("Alert not found with id: " + id);
        }
        patientAlertRepository.deleteById(id);
    }
}
