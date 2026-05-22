package com.example.Ehpad.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientHistoriqueDTO {
    private Long id;
    
    private Long patientId;
    private Long acteurId;
    
    private String typeAction;
    private String description;
    
    private LocalDateTime dateAction;
    
    // Audit
    private LocalDateTime createdAt;
}
