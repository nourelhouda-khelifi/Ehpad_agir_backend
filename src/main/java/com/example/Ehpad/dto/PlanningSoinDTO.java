package com.example.Ehpad.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.Ehpad.entity.PlanningStatut;
import com.example.Ehpad.entity.SoinMoment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanningSoinDTO {
    private Long id;
    
    @NotNull(message = "Patient requis")
    private Long patientId;
    
    @NotNull(message = "Type de soin requis")
    private Long typeSoinId;
    
    private Long aideSoignantId;
    
    @NotNull(message = "Date prévue requise")
    private LocalDate datePrevue;
    
    private String jourSemaine;
    private String heurePrevue;
    private Integer dureePrevue;
    
    private SoinMoment moment;
    private PlanningStatut statut;
    private String commentaire;
    
    // Audit
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Pour les réponses - relations dénormalisées
    private PatientDTO patient;
    private TypeSoinDTO typeSoin;
    private AideSoignantDTO aideSoignant;
}
