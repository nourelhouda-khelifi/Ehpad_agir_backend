package com.example.Ehpad.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "patient_notes", indexes = {
    @Index(name = "idx_patient_notes_patient", columnList = "patient_id"),
    @Index(name = "idx_patient_notes_date", columnList = "date_note")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientNote {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Patient patient;
    
    @Column(name = "auteur_id")
    private Long auteurId;
    
    @Column(name = "date_note")
    private LocalDateTime dateNote;
    
    @Column(name = "contenu", nullable = false, columnDefinition = "TEXT")
    private String contenu;
    
    @Column(name = "important")
    private Boolean important;
    
    @Column(name = "categorie_note", length = 100)
    private String categorieNote;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
