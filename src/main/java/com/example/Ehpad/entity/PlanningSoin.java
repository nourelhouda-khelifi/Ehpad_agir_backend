package com.example.Ehpad.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "planning_soins", indexes = {
    @Index(name = "idx_planning_soins_patient", columnList = "patient_id"),
    @Index(name = "idx_planning_soins_type_soin", columnList = "type_soin_id"),
    @Index(name = "idx_planning_soins_aide_soignant", columnList = "aide_soignant_id"),
    @Index(name = "idx_planning_soins_date", columnList = "date_prevue"),
    @Index(name = "idx_planning_soins_statut", columnList = "statut")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanningSoin {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Patient patient;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_soin_id", nullable = false)
    private TypeSoin typeSoin;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aide_soignant_id")
    private AideSoignant aideSoignant;
    
    @Column(name = "date_prevue")
    private LocalDate datePrevue;
    
    @Column(name = "jour_semaine", length = 20)
    private String jourSemaine;
    
    @Column(name = "heure_prevue", length = 5)
    private String heurePrevue;
    
    @Column(name = "duree_prevue")
    private Integer dureePrevue;
    
    @Column(name = "moment", length = 10)
    @Enumerated(EnumType.STRING)
    private SoinMoment moment;
    
    @Column(name = "statut", length = 20)
    @Enumerated(EnumType.STRING)
    private PlanningStatut statut;
    
    @Column(name = "commentaire", columnDefinition = "TEXT")
    private String commentaire;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
