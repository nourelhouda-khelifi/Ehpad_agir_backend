package com.example.Ehpad.controller;

import java.util.List;

import com.example.Ehpad.dto.AideSoignantCreateDTO;
import com.example.Ehpad.dto.AideSoignantDTO;
import com.example.Ehpad.service.AideSoignantService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@RestController
@RequestMapping("/api/aides-soignants")
@PreAuthorize("hasAnyRole('INFIRMIERE', 'ADMIN')")
@Slf4j
public class AideSoignantController {
    private final AideSoignantService aideSoignantService;
    
    public AideSoignantController(AideSoignantService aideSoignantService) {
        this.aideSoignantService = aideSoignantService;
    }
    
    @GetMapping
    public ResponseEntity<List<AideSoignantDTO>> getAllAidesSoignants() {
        log.info("GET /api/aides-soignants");
        return ResponseEntity.ok(aideSoignantService.getAllAidesSoignants());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AideSoignantDTO> getAideSoignantById(@PathVariable Long id) {
        log.info("GET /api/aides-soignants/{}", id);
        return ResponseEntity.ok(aideSoignantService.getAideSoignantById(id));
    }
    
    @GetMapping("/code/{code}")
    public ResponseEntity<AideSoignantDTO> getAideSoignantByCode(@PathVariable String code) {
        log.info("GET /api/aides-soignants/code/{}", code);
        return ResponseEntity.ok(aideSoignantService.getAideSoignantByCode(code));
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createAideSoignant(@Valid @RequestBody AideSoignantCreateDTO aideSoignantDTO) {
        log.info("POST /api/aides-soignants - Creating care staff: {}", aideSoignantDTO.getCode());
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(aideSoignantService.createAideSoignant(aideSoignantDTO));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AideSoignantDTO> updateAideSoignant(@PathVariable Long id, @Valid @RequestBody AideSoignantDTO aideSoignantDTO) {
        log.info("PUT /api/aides-soignants/{} - Updating care staff", id);
        return ResponseEntity.ok(aideSoignantService.updateAideSoignant(id, aideSoignantDTO));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAideSoignant(@PathVariable Long id) {
        log.info("DELETE /api/aides-soignants/{}", id);
        aideSoignantService.deleteAideSoignant(id);
        return ResponseEntity.noContent().build();
    }
}
