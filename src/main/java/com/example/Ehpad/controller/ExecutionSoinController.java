package com.example.Ehpad.controller;

import java.time.LocalDate;
import java.util.List;

import com.example.Ehpad.dto.ExecutionSoinCreateDTO;
import com.example.Ehpad.dto.ExecutionSoinDTO;
import com.example.Ehpad.service.ExecutionSoinService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/executions")
@Slf4j
public class ExecutionSoinController {
    private final ExecutionSoinService executionSoinService;
    
    public ExecutionSoinController(ExecutionSoinService executionSoinService) {
        this.executionSoinService = executionSoinService;
    }
    
    @GetMapping
    public ResponseEntity<List<ExecutionSoinDTO>> getAllExecutionsSoins() {
        log.info("GET /api/executions");
        return ResponseEntity.ok(executionSoinService.getAllExecutionsSoins());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ExecutionSoinDTO> getExecutionSoinById(@PathVariable Long id) {
        log.info("GET /api/executions/{}", id);
        return ResponseEntity.ok(executionSoinService.getExecutionSoinById(id));
    }
    
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<ExecutionSoinDTO>> getExecutionsByPatient(@PathVariable Long patientId) {
        log.info("GET /api/executions/patient/{}", patientId);
        return ResponseEntity.ok(executionSoinService.getExecutionsByPatientId(patientId));
    }
    
    @GetMapping("/aide-soignant/{aideSoignantId}")
    public ResponseEntity<List<ExecutionSoinDTO>> getExecutionsByAideSoignant(@PathVariable Long aideSoignantId) {
        log.info("GET /api/executions/aide-soignant/{}", aideSoignantId);
        return ResponseEntity.ok(executionSoinService.getExecutionsByAideSoignantId(aideSoignantId));
    }
    
    @GetMapping("/date/{date}")
    public ResponseEntity<List<ExecutionSoinDTO>> getExecutionsByDate(@PathVariable LocalDate date) {
        log.info("GET /api/executions/date/{}", date);
        return ResponseEntity.ok(executionSoinService.getExecutionsByDate(date));
    }
    
    @GetMapping("/patient/{patientId}/date/{date}")
    public ResponseEntity<List<ExecutionSoinDTO>> getExecutionsByPatientAndDate(@PathVariable Long patientId, @PathVariable LocalDate date) {
        log.info("GET /api/executions/patient/{}/date/{}", patientId, date);
        return ResponseEntity.ok(executionSoinService.getExecutionsByPatientAndDate(patientId, date));
    }
    
    @PostMapping
    public ResponseEntity<ExecutionSoinDTO> createExecutionSoin(@Valid @RequestBody ExecutionSoinCreateDTO executionSoinDTO) {
        log.info("POST /api/executions - Creating execution");
        ExecutionSoinDTO createdExecutionSoin = executionSoinService.createExecutionSoin(executionSoinDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExecutionSoin);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ExecutionSoinDTO> updateExecutionSoin(@PathVariable Long id, @Valid @RequestBody ExecutionSoinDTO executionSoinDTO) {
        log.info("PUT /api/executions/{} - Updating execution", id);
        return ResponseEntity.ok(executionSoinService.updateExecutionSoin(id, executionSoinDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExecutionSoin(@PathVariable Long id) {
        log.info("DELETE /api/executions/{}", id);
        executionSoinService.deleteExecutionSoin(id);
        return ResponseEntity.noContent().build();
    }
}
