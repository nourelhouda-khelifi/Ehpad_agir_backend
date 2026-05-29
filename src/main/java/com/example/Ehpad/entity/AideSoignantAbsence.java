package com.example.Ehpad.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "aide_soignant_absences")
public class AideSoignantAbsence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "aide_soignant_id", nullable = false)
    private Long aideSoignantId;

    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @Column(name = "date_fin", nullable = false)
    private LocalDate dateFin;

    @Column(name = "raison", nullable = false)
    private String raison; // MALADIE, CONGE, AUTRE

    @Column(name = "statut", nullable = false)
    private String statut = "ACTIVE"; // ACTIVE, ANNULEE

    @Column(name = "commentaire")
    private String commentaire;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Constructeurs
    public AideSoignantAbsence() {
    }

    public AideSoignantAbsence(Long aideSoignantId, LocalDate dateDebut, LocalDate dateFin, String raison) {
        this.aideSoignantId = aideSoignantId;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.raison = raison;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAideSoignantId() {
        return aideSoignantId;
    }

    public void setAideSoignantId(Long aideSoignantId) {
        this.aideSoignantId = aideSoignantId;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public String getRaison() {
        return raison;
    }

    public void setRaison(String raison) {
        this.raison = raison;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
