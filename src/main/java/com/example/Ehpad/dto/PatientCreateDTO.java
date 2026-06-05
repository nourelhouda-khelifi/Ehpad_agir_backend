package com.example.Ehpad.dto;

import com.example.Ehpad.entity.GroupeCoucher;
import com.example.Ehpad.entity.GroupeWC;
import com.example.Ehpad.entity.PatientCategorie;
import com.example.Ehpad.entity.PatientStatut;
import com.example.Ehpad.entity.Priorite;

import jakarta.validation.constraints.Min;
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
public class PatientCreateDTO {
    @NotBlank(message = "Numéro de chambre requis")
    private String numeroChambre;

    @NotBlank(message = "Nom requis")
    private String nom;

    @NotBlank(message = "Prénom requis")
    private String prenom;

    @NotNull(message = "Étage requis")
    @Min(value = 0, message = "Étage doit être positif")
    private Integer etage;

    private PatientStatut statut;
    private PatientCategorie categorie;
    private String profil;
    private Priorite priorite;

    private Integer tempsToiletteLit;
    private Integer tempsToiletteVasque;
    private Integer tempsToiletteMoyen;
    private Integer tempsWcMoyen;
    private Integer tempsCoucherMoyen;

    private Boolean aideSoignant;
    private Boolean petitDejeunerAide;
    private Boolean sansDouche;
    private GroupeCoucher groupeCoucher;
    private GroupeWC groupeWC;
}