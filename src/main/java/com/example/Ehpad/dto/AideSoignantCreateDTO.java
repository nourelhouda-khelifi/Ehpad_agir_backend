package com.example.Ehpad.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AideSoignantCreateDTO {
    @NotBlank(message = "Code requis")
    @Size(max = 10, message = "Code ne doit pas dépasser 10 caractères")
    private String code;

    @NotBlank(message = "Nom requis")
    private String nom;

    @NotBlank(message = "Prénom requis")
    private String prenom;

    private String secteur;
    private String color;
    private Boolean actif;
}