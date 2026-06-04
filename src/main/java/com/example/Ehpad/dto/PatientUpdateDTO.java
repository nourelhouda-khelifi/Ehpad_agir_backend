package com.example.Ehpad.dto;

import com.example.Ehpad.entity.GroupeCoucher;
import com.example.Ehpad.entity.PatientCategorie;
import com.example.Ehpad.entity.PatientStatut;
import com.example.Ehpad.entity.Priorite;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour les mises à jour partielles de patient
 * Tous les champs sont optionnels pour permettre des mises à jour partielles
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientUpdateDTO {
    private String numeroChambre;
    private String nom;
    private String prenom;
    private Integer etage;
    private PatientStatut statut;
    private PatientCategorie categorie;
    private String profil;
    private String priorite; // Changed to String to handle empty string from frontend

    // Temps de soins (en minutes)
    private Integer tempsToiletteLit;
    private Integer tempsToiletteVasque;
    private Integer tempsToiletteMoyen;
    private Integer tempsWcMoyen;
    private Integer tempsCoucherMoyen;

    // Flags
    private Boolean aideSoignant;
    private Boolean petitDejeunerAide;
    private Boolean sansDouche;
    private GroupeCoucher groupeCoucher;
}
