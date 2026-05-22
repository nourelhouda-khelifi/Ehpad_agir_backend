package com.example.Ehpad.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "execution_soins", indexes = {
    @Index(name = "idx_execution_soins_patient", columnList = "patient_id"),
    @Index(name = "idx_execution_soins_planning", columnList = "planning_soin_id"),
    @Index(name = "idx_execution_soins_date", columnList = "date_execution"),
    @Index(name = "idx_execution_soins_statut", columnList = "statut")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExecutionSoin {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planning_soin_id")
    private PlanningSoin planningSoin;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_soin_id", nullable = false)
    private TypeSoin typeSoin;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aide_soignant_id")
    private AideSoignant aideSoignant;
    
    @Column(name = "date_execution")
    private LocalDate dateExecution;
    
    @Column(name = "heure_execution", length = 5)
    private String heureExecution;
    
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
