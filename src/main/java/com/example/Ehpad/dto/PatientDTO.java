package com.example.Ehpad.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.Ehpad.entity.GroupeCoucher;
import com.example.Ehpad.entity.PatientCategorie;
import com.example.Ehpad.entity.PatientStatut;
import com.example.Ehpad.entity.Priorite;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientDTO {
    private Long id;

    @NotBlank(message = "Numéro de chambre requis")
    private String numeroChambre;

    @NotBlank(message = "Nom requis")
    private String nom;

    @NotBlank(message = "Prénom requis")
    private String prenom;

    @Min(value = 0, message = "Étage doit être positif")
    private Integer etage;

    private PatientStatut statut;
    private PatientCategorie categorie;
    private String profil;
    private Priorite priorite;

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

    // Audit
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Relations
    private List<PatientNoteDTO> notes;
    private List<PatientAlertDTO> alertes;
    private List<PatientHistoriqueDTO> historique;
}
