package com.example.Ehpad.controller;

import java.util.List;

import com.example.Ehpad.dto.PatientAlertDTO;
import com.example.Ehpad.service.PatientAlertService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/alertes")
@Slf4j
public class PatientAlertController {
    private final PatientAlertService patientAlertService;
    
    public PatientAlertController(PatientAlertService patientAlertService) {
        this.patientAlertService = patientAlertService;
    }
    
    @GetMapping
    public ResponseEntity<List<PatientAlertDTO>> getAllAlerts() {
        log.info("GET /api/alertes");
        return ResponseEntity.ok(patientAlertService.getAllAlerts());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PatientAlertDTO> getAlertById(@PathVariable Long id) {
        log.info("GET /api/alertes/{}", id);
        return ResponseEntity.ok(patientAlertService.getAlertById(id));
    }
    
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<PatientAlertDTO>> getAlertsByPatient(@PathVariable Long patientId) {
        log.info("GET /api/alertes/patient/{}", patientId);
        return ResponseEntity.ok(patientAlertService.getAlertsByPatientId(patientId));
    }
    
    @GetMapping("/aide-soignant/{aideSoignantId}")
    public ResponseEntity<List<PatientAlertDTO>> getAlertsByAideSoignant(@PathVariable Long aideSoignantId) {
        log.info("GET /api/alertes/aide-soignant/{}", aideSoignantId);
        return ResponseEntity.ok(patientAlertService.getAlertsByAideSoignantId(aideSoignantId));
    }
    
    @GetMapping("/unresolved")
    public ResponseEntity<List<PatientAlertDTO>> getUnresolvedAlerts() {
        log.info("GET /api/alertes/unresolved");
        return ResponseEntity.ok(patientAlertService.getUnresolvedAlerts());
    }
    
    @GetMapping("/resolved")
    public ResponseEntity<List<PatientAlertDTO>> getResolvedAlerts() {
        log.info("GET /api/alertes/resolved");
        return ResponseEntity.ok(patientAlertService.getResolvedAlerts());
    }
    
    @PostMapping
    public ResponseEntity<PatientAlertDTO> createAlert(@Valid @RequestBody PatientAlertDTO patientAlertDTO) {
        log.info("POST /api/alertes - Creating alert");
        PatientAlertDTO createdAlert = patientAlertService.createAlert(patientAlertDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAlert);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PatientAlertDTO> updateAlert(@PathVariable Long id, @Valid @RequestBody PatientAlertDTO patientAlertDTO) {
        log.info("PUT /api/alertes/{} - Updating alert", id);
        return ResponseEntity.ok(patientAlertService.updateAlert(id, patientAlertDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlert(@PathVariable Long id) {
        log.info("DELETE /api/alertes/{}", id);
        patientAlertService.deleteAlert(id);
        return ResponseEntity.noContent().build();
    }
}
