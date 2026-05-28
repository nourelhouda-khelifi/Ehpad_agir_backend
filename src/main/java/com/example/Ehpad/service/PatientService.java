package com.example.Ehpad.service;

import java.util.List;

import com.example.Ehpad.dto.PatientCreateDTO;
import com.example.Ehpad.dto.PatientDTO;
import com.example.Ehpad.entity.Patient;
import com.example.Ehpad.entity.PatientCategorie;
import com.example.Ehpad.entity.PatientStatut;
import com.example.Ehpad.mapper.PatientMapper;
import com.example.Ehpad.repository.PatientRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    
    public PatientService(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }
    
    public List<PatientDTO> getAllPatients() {
        log.info("Fetching all patients");
        return patientMapper.toDTOList(patientRepository.findAll());
    }
    
    public PatientDTO getPatientById(Long id) {
        log.info("Fetching patient with id: {}", id);
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + id));
        return patientMapper.toDTO(patient);
    }
    
    public PatientDTO createPatient(PatientCreateDTO patientDTO) {
        log.info("Creating patient: {}", patientDTO.getNom());
        Patient patient = patientMapper.toEntity(patientDTO);
        Patient savedPatient = patientRepository.save(patient);
        return patientMapper.toDTO(savedPatient);
    }
    
    public PatientDTO updatePatient(Long id, PatientDTO patientDTO) {
        log.info("Updating patient with id: {}", id);
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + id));
        
        patient.setNom(patientDTO.getNom());
        patient.setPrenom(patientDTO.getPrenom());
        patient.setNumeroChambre(patientDTO.getNumeroChambre());
        patient.setEtage(patientDTO.getEtage());
        patient.setStatut(patientDTO.getStatut());
        patient.setCategorie(patientDTO.getCategorie());
        patient.setProfil(patientDTO.getProfil());
        patient.setTempsToiletteLit(patientDTO.getTempsToiletteLit());
        patient.setTempsToiletteVasque(patientDTO.getTempsToiletteVasque());
        patient.setTempsToiletteMoyen(patientDTO.getTempsToiletteMoyen());
        patient.setTempsWcMoyen(patientDTO.getTempsWcMoyen());
        patient.setTempsCoucherMoyen(patientDTO.getTempsCoucherMoyen());
        patient.setAideSoignant(patientDTO.getAideSoignant());
        patient.setPetitDejeunerAide(patientDTO.getPetitDejeunerAide());
        patient.setSansDouche(patientDTO.getSansDouche());
        
        Patient updatedPatient = patientRepository.save(patient);
        return patientMapper.toDTO(updatedPatient);
    }
    
    public void deletePatient(Long id) {
        log.info("Deleting patient with id: {}", id);
        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }
    
    public List<PatientDTO> getPatientsByEtage(Integer etage) {
        log.info("Fetching patients from floor: {}", etage);
        return patientMapper.toDTOList(patientRepository.findByEtage(etage));
    }
    
    public List<PatientDTO> getPatientsByStatut(String statut) {
        log.info("Fetching patients with status: {}", statut);
        return patientMapper.toDTOList(patientRepository.findByStatut(PatientStatut.valueOf(statut)));
    }
    
    public List<PatientDTO> getPatientsByCategorie(String categorie) {
        log.info("Fetching patients with category: {}", categorie);
        return patientMapper.toDTOList(patientRepository.findByCategorie(PatientCategorie.valueOf(categorie)));
    }
    
    public List<PatientDTO> getPatientsByNumeroChambre(String numeroChambre) {
        log.info("Fetching patients by room number: {}", numeroChambre);
        return patientMapper.toDTOList(patientRepository.findByNumeroChambreContainingIgnoreCase(numeroChambre));
    }
}
