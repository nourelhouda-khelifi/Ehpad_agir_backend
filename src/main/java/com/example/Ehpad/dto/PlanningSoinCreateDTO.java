package com.example.Ehpad.dto;

import java.time.LocalDate;

import com.example.Ehpad.entity.PlanningStatut;
import com.example.Ehpad.entity.SoinMoment;

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
public class PlanningSoinCreateDTO {
    @NotNull(message = "Patient requis")
    private Long patientId;

    @NotNull(message = "Type de soin requis")
    private Long typeSoinId;

    private Long aideSoignantId;

    @NotNull(message = "Date prévue requise")
    private LocalDate datePrevue;

    @Size(max = 20, message = "Jour de semaine ne doit pas dépasser 20 caractères")
    private String jourSemaine;

    @Size(max = 5, message = "Heure prévue doit être au format HH:mm")
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "Heure prévue doit être au format HH:mm")
    private String heurePrevue;

    private Integer dureePrevue;

    private SoinMoment moment;
    private PlanningStatut statut;
    private String commentaire;
}