package com.example.Ehpad.dto;

import java.time.LocalDateTime;

import com.example.Ehpad.entity.AlerteNiveau;
import com.example.Ehpad.entity.AlerteType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientAlertDTO {
    private Long id;
    
    private Long patientId;
    private Long aideSoignantId;
    
    private AlerteType type;
    private AlerteNiveau niveau;
    private String message;
    private Boolean resolue;
    
    private LocalDateTime dateCreation;
    private LocalDateTime dateResolution;
    
    // Audit
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
