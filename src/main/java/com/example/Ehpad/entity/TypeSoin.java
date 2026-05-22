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
@Table(name = "type_soins", indexes = {
    @Index(name = "idx_type_soins_code", columnList = "code")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeSoin {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "code", unique = true, nullable = false, length = 50)
    private String code;
    
    @Column(name = "libelle", nullable = false, length = 255)
    private String libelle;
    
    @Column(name = "duree_par_defaut")
    private Integer dureeParDefaut;
    
    @Column(name = "actif")
    private Boolean actif;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
