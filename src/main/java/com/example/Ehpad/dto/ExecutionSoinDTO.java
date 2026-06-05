package com.example.Ehpad.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.Ehpad.entity.PlanningStatut;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExecutionSoinDTO {
    private Long id;
    
    private Long planningSoinId;
    
    @NotNull(message = "Patient requis")
    private Long patientId;
    
    @NotNull(message = "Type de soin requis")
    private Long typeSoinId;
    
    private Long aideSoignantId;
    
    @NotNull(message = "Date d'exécution requise")
    private LocalDate dateExecution;
    
    private String heureExecution;
    private PlanningStatut statut;
    private String commentaire;
    private String notesSoignant;
    
    // Audit
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Pour les réponses - relations dénormalisées
    private PatientDTO patient;
    private TypeSoinDTO typeSoin;
    private AideSoignantDTO aideSoignant;
}
