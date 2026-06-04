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

import java.time.LocalDateTime;

@Entity
@Table(name = "patient_alerts", indexes = {
    @Index(name = "idx_patient_alerts_patient", columnList = "patient_id"),
    @Index(name = "idx_patient_alerts_aide_soignant", columnList = "aide_soignant_id"),
    @Index(name = "idx_patient_alerts_resolue", columnList = "resolue"),
    @Index(name = "idx_patient_alerts_niveau", columnList = "niveau")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientAlert {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Patient patient;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aide_soignant_id")
    private AideSoignant aideSoignant;
    
    @Column(name = "type", length = 50)
    @Enumerated(EnumType.STRING)
    private AlerteType type;
    
    @Column(name = "niveau", length = 20)
    @Enumerated(EnumType.STRING)
    private AlerteNiveau niveau;
    
    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
    
    @Column(name = "resolue")
    private Boolean resolue;
    
    @Column(name = "date_resolution")
    private LocalDateTime dateResolution;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
