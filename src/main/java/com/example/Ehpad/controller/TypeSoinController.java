package com.example.Ehpad.controller;

import java.util.List;

import com.example.Ehpad.dto.TypeSoinCreateDTO;
import com.example.Ehpad.dto.TypeSoinDTO;
import com.example.Ehpad.service.TypeSoinService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/types-soins")
@PreAuthorize("hasAnyRole('INFIRMIERE', 'ADMIN')")
@Slf4j
public class TypeSoinController {
    private final TypeSoinService typeSoinService;
    
    public TypeSoinController(TypeSoinService typeSoinService) {
        this.typeSoinService = typeSoinService;
    }
    
    @GetMapping
    public ResponseEntity<List<TypeSoinDTO>> getAllTypesSoins() {
        log.info("GET /api/types-soins");
        return ResponseEntity.ok(typeSoinService.getAllTypesSoins());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TypeSoinDTO> getTypeSoinById(@PathVariable Long id) {
        log.info("GET /api/types-soins/{}", id);
        return ResponseEntity.ok(typeSoinService.getTypeSoinById(id));
    }
    
    @GetMapping("/code/{code}")
    public ResponseEntity<TypeSoinDTO> getTypeSoinByCode(@PathVariable String code) {
        log.info("GET /api/types-soins/code/{}", code);
        return ResponseEntity.ok(typeSoinService.getTypeSoinByCode(code));
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TypeSoinDTO> createTypeSoin(@Valid @RequestBody TypeSoinCreateDTO typeSoinDTO) {
        log.info("POST /api/types-soins - Creating care type: {}", typeSoinDTO.getCode());
        TypeSoinDTO createdTypeSoin = typeSoinService.createTypeSoin(typeSoinDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTypeSoin);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TypeSoinDTO> updateTypeSoin(@PathVariable Long id, @Valid @RequestBody TypeSoinDTO typeSoinDTO) {
        log.info("PUT /api/types-soins/{} - Updating care type", id);
        return ResponseEntity.ok(typeSoinService.updateTypeSoin(id, typeSoinDTO));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTypeSoin(@PathVariable Long id) {
        log.info("DELETE /api/types-soins/{}", id);
        typeSoinService.deleteTypeSoin(id);
        return ResponseEntity.noContent().build();
    }
}
