package com.example.Ehpad.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "patient_historique", indexes = {
    @Index(name = "idx_patient_historique_patient", columnList = "patient_id"),
    @Index(name = "idx_patient_historique_date", columnList = "date_action")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientHistorique {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    
    @Column(name = "acteur_id")
    private Long acteurId;
    
    @Column(name = "type_action", length = 100)
    private String typeAction;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "date_action")
    private LocalDateTime dateAction;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
