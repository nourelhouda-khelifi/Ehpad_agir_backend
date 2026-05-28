package com.example.Ehpad.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeSoinCreateDTO {
    @NotBlank(message = "Code requis")
    @Size(max = 50, message = "Code ne doit pas dépasser 50 caractères")
    private String code;

    @NotBlank(message = "Libellé requis")
    private String libelle;

    @NotNull(message = "Durée par défaut requise")
    private Integer dureeParDefaut;

    private Boolean actif;
}