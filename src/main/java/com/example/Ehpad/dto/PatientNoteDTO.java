package com.example.Ehpad.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientNoteDTO {
    private Long id;
    
    private Long patientId;
    private Long auteurId;
    
    @NotBlank(message = "Contenu de la note requis")
    private String contenu;
    
    private LocalDateTime dateNote;
    private Boolean important;
    private String categorieNote;
    
    // Audit
    private LocalDateTime createdAt;
}
