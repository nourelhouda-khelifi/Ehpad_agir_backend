package com.example.Ehpad.controller;

import java.time.LocalDate;
import java.util.List;

import com.example.Ehpad.dto.PlanningSoinCreateDTO;
import com.example.Ehpad.dto.PlanningSoinDTO;
import com.example.Ehpad.service.PlanningSoinService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/plannings")
@Slf4j
public class PlanningSoinController {
    private final PlanningSoinService planningSoinService;
    
    public PlanningSoinController(PlanningSoinService planningSoinService) {
        this.planningSoinService = planningSoinService;
    }
    
    @GetMapping
    public ResponseEntity<List<PlanningSoinDTO>> getAllPlanningsSoins() {
        log.info("GET /api/plannings");
        return ResponseEntity.ok(planningSoinService.getAllPlanningsSoins());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PlanningSoinDTO> getPlanningSoinById(@PathVariable Long id) {
        log.info("GET /api/plannings/{}", id);
        return ResponseEntity.ok(planningSoinService.getPlanningSoinById(id));
    }
    
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<PlanningSoinDTO>> getPlanningsByPatient(@PathVariable Long patientId) {
        log.info("GET /api/plannings/patient/{}", patientId);
        return ResponseEntity.ok(planningSoinService.getPlanningsByPatientId(patientId));
    }
    
    @GetMapping("/aide-soignant/{aideSoignantId}")
    public ResponseEntity<List<PlanningSoinDTO>> getPlanningsByAideSoignant(@PathVariable Long aideSoignantId) {
        log.info("GET /api/plannings/aide-soignant/{}", aideSoignantId);
        return ResponseEntity.ok(planningSoinService.getPlanningsByAideSoignantId(aideSoignantId));
    }
    
    @GetMapping("/date/{date}")
    public ResponseEntity<List<PlanningSoinDTO>> getPlanningsByDate(@PathVariable LocalDate date) {
        log.info("GET /api/plannings/date/{}", date);
        return ResponseEntity.ok(planningSoinService.getPlanningsByDate(date));
    }
    
    @GetMapping("/patient/{patientId}/date/{date}")
    public ResponseEntity<List<PlanningSoinDTO>> getPlanningsByPatientAndDate(@PathVariable Long patientId, @PathVariable LocalDate date) {
        log.info("GET /api/plannings/patient/{}/date/{}", patientId, date);
        return ResponseEntity.ok(planningSoinService.getPlanningsByPatientAndDate(patientId, date));
    }
    
    @PostMapping
    public ResponseEntity<PlanningSoinDTO> createPlanningSoin(@Valid @RequestBody PlanningSoinCreateDTO planningSoinDTO) {
        log.info("POST /api/plannings - Creating planning");
        PlanningSoinDTO createdPlanningSoin = planningSoinService.createPlanningSoin(planningSoinDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlanningSoin);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PlanningSoinDTO> updatePlanningSoin(@PathVariable Long id, @Valid @RequestBody PlanningSoinDTO planningSoinDTO) {
        log.info("PUT /api/plannings/{} - Updating planning", id);
        return ResponseEntity.ok(planningSoinService.updatePlanningSoin(id, planningSoinDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlanningSoin(@PathVariable Long id) {
        log.info("DELETE /api/plannings/{}", id);
        planningSoinService.deletePlanningSoin(id);
        return ResponseEntity.noContent().build();
    }
}
