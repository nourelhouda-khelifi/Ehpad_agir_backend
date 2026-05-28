package com.example.Ehpad.dto;

import com.example.Ehpad.entity.AlerteNiveau;
import com.example.Ehpad.entity.AlerteType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientAlertCreateDTO {
    @NotNull(message = "Patient requis")
    private Long patientId;

    @NotNull(message = "Type d'alerte requis")
    private AlerteType type;

    @NotNull(message = "Niveau d'alerte requis")
    private AlerteNiveau niveau;

    @NotBlank(message = "Message requis")
    private String message;
}