package com.example.Ehpad.dto;

import java.time.LocalDate;

import com.example.Ehpad.entity.PlanningStatut;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExecutionSoinCreateDTO {
    private Long planningSoinId;

    @NotNull(message = "Patient requis")
    private Long patientId;

    @NotNull(message = "Type de soin requis")
    private Long typeSoinId;

    private Long aideSoignantId;
    private Long secondAideSoignantId; // Pour assignations 2 aides

    @NotNull(message = "Date d'exécution requise")
    private LocalDate dateExecution;

    @Size(max = 5, message = "Heure d'exécution doit être au format HH:mm")
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "Heure d'exécution doit être au format HH:mm")
    private String heureExecution;

    private PlanningStatut statut;
    private String commentaire;
    private String notesSoignant;
}