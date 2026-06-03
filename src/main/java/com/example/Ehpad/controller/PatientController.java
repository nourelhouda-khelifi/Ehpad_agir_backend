package com.example.Ehpad.controller;

import java.util.List;

import com.example.Ehpad.dto.PatientCreateDTO;
import com.example.Ehpad.dto.PatientDTO;
import com.example.Ehpad.dto.PatientUpdateDTO;
import com.example.Ehpad.entity.PatientCategorie;
import com.example.Ehpad.entity.PatientStatut;
import com.example.Ehpad.service.PatientService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/patients")
@Slf4j
public class PatientController {
    private final PatientService patientService;
    
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }
    
    @GetMapping
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        log.info("GET /api/patients");
        return ResponseEntity.ok(patientService.getAllPatients());
    }
    
    @GetMapping("/room/{numeroChambre}")
    public ResponseEntity<List<PatientDTO>> getPatientByRoom(@PathVariable String numeroChambre) {
        log.info("GET /api/patients/room/{}", numeroChambre);
        return ResponseEntity.ok(patientService.getPatientsByNumeroChambre(numeroChambre));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Long id) {
        log.info("GET /api/patients/{}", id);
        return ResponseEntity.ok(patientService.getPatientById(id));
    }
    
    @GetMapping("/floor/{etage}")
    public ResponseEntity<List<PatientDTO>> getPatientsByFloor(@PathVariable Integer etage) {
        log.info("GET /api/patients/floor/{}", etage);
        return ResponseEntity.ok(patientService.getPatientsByEtage(etage));
    }
    
    @GetMapping("/status/{statut}")
    public ResponseEntity<List<PatientDTO>> getPatientsByStatus(@PathVariable String statut) {
        log.info("GET /api/patients/status/{}", statut);
        return ResponseEntity.ok(patientService.getPatientsByStatut(statut));
    }
    
    @GetMapping("/category/{categorie}")
    public ResponseEntity<List<PatientDTO>> getPatientsByCategory(@PathVariable String categorie) {
        log.info("GET /api/patients/category/{}", categorie);
        return ResponseEntity.ok(patientService.getPatientsByCategorie(categorie));
    }
    
    @PostMapping
    public ResponseEntity<PatientDTO> createPatient(@Valid @RequestBody PatientCreateDTO patientDTO) {
        log.info("POST /api/patients - Creating patient: {}", patientDTO.getNom());
        PatientDTO createdPatient = patientService.createPatient(patientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable Long id, @RequestBody PatientUpdateDTO patientDTO) {
        log.info("PUT /api/patients/{} - Updating patient", id);
        return ResponseEntity.ok(patientService.updatePatient(id, patientDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        log.info("DELETE /api/patients/{}", id);
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
