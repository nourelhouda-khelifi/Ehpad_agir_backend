package com.example.Ehpad.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "numero_chambre", length = 20)
    private String numeroChambre;
    
    @Column(name = "nom", nullable = false, length = 100)
    private String nom;
    
    @Column(name = "prenom", nullable = false, length = 100)
    private String prenom;
    
    @Column(name = "etage")
    private Integer etage;
    
    @Column(name = "statut", length = 20)
    @Enumerated(EnumType.STRING)
    private PatientStatut statut;
    
    @Column(name = "categorie", length = 10)
    @Enumerated(EnumType.STRING)
    private PatientCategorie categorie;
    
    @Column(name = "profil", length = 100)
    private String profil;
    
    @Column(name = "priorite", length = 20)
    @Enumerated(EnumType.STRING)
    private Priorite priorite;
    
    @Column(name = "temps_toilette_lit")
    private Integer tempsToiletteLit;
    
    @Column(name = "temps_toilette_vasque")
    private Integer tempsToiletteVasque;
    
    @Column(name = "temps_toilette_moyen")
    private Integer tempsToiletteMoyen;
    
    @Column(name = "temps_wc_moyen")
    private Integer tempsWcMoyen;
    
    @Column(name = "temps_coucher_moyen")
    private Integer tempsCoucherMoyen;
    
    @Column(name = "aide_soignant")
    private Boolean aideSoignant;
    
    @Column(name = "petit_dejeuner_aide")
    private Boolean petitDejeunerAide;
    
    @Column(name = "sans_douche")
    private Boolean sansDouche;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
