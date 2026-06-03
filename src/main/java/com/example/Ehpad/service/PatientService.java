package com.example.Ehpad.service;

import java.util.List;

import com.example.Ehpad.dto.PatientCreateDTO;
import com.example.Ehpad.dto.PatientDTO;
import com.example.Ehpad.dto.PatientUpdateDTO;
import com.example.Ehpad.entity.Patient;
import com.example.Ehpad.entity.PatientCategorie;
import com.example.Ehpad.entity.PatientStatut;
import com.example.Ehpad.entity.Priorite;
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
        patient.setPriorite(patientDTO.getPriorite());
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

    /**
     * Mise à jour partielle du patient (PATCH-like behavior)
     * Seuls les champs non-null du DTO sont mis à jour
     */
    public PatientDTO updatePatient(Long id, PatientUpdateDTO patientDTO) {
        log.info("Partially updating patient with id: {}", id);
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + id));

        log.debug("Patient avant modification: {}", patient);

        // Mettre à jour uniquement les champs non-null
        if (patientDTO.getNom() != null) {
            log.debug("Mise à jour nom: {} -> {}", patient.getNom(), patientDTO.getNom());
            patient.setNom(patientDTO.getNom());
        }
        if (patientDTO.getPrenom() != null) {
            patient.setPrenom(patientDTO.getPrenom());
        }
        if (patientDTO.getNumeroChambre() != null) {
            patient.setNumeroChambre(patientDTO.getNumeroChambre());
        }
        if (patientDTO.getEtage() != null) {
            patient.setEtage(patientDTO.getEtage());
        }
        if (patientDTO.getStatut() != null) {
            patient.setStatut(patientDTO.getStatut());
        }
        if (patientDTO.getCategorie() != null) {
            log.debug("Mise à jour categorie: {} -> {}", patient.getCategorie(), patientDTO.getCategorie());
            patient.setCategorie(patientDTO.getCategorie());
        }
        if (patientDTO.getProfil() != null) {
            patient.setProfil(patientDTO.getProfil());
        }
        // Handle priorite: convert empty string to null
        if (patientDTO.getPriorite() != null && !patientDTO.getPriorite().isEmpty()) {
            try {
                patient.setPriorite(Priorite.valueOf(patientDTO.getPriorite()));
            } catch (IllegalArgumentException e) {
                log.warn("Invalid priorite value: {}", patientDTO.getPriorite());
            }
        } else {
            patient.setPriorite(null);
        }
        if (patientDTO.getTempsToiletteLit() != null) {
            patient.setTempsToiletteLit(patientDTO.getTempsToiletteLit());
        }
        if (patientDTO.getTempsToiletteVasque() != null) {
            patient.setTempsToiletteVasque(patientDTO.getTempsToiletteVasque());
        }
        if (patientDTO.getTempsToiletteMoyen() != null) {
            patient.setTempsToiletteMoyen(patientDTO.getTempsToiletteMoyen());
        }
        if (patientDTO.getTempsWcMoyen() != null) {
            patient.setTempsWcMoyen(patientDTO.getTempsWcMoyen());
        }
        if (patientDTO.getTempsCoucherMoyen() != null) {
            patient.setTempsCoucherMoyen(patientDTO.getTempsCoucherMoyen());
        }
        if (patientDTO.getAideSoignant() != null) {
            patient.setAideSoignant(patientDTO.getAideSoignant());
        }
        if (patientDTO.getPetitDejeunerAide() != null) {
            patient.setPetitDejeunerAide(patientDTO.getPetitDejeunerAide());
        }
        if (patientDTO.getSansDouche() != null) {
            patient.setSansDouche(patientDTO.getSansDouche());
        }

        Patient updatedPatient = patientRepository.save(patient);
        log.info("Patient {} mise à jour avec succès. Nouvelle catégorie: {}", id, updatedPatient.getCategorie());
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
