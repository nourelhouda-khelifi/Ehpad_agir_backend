package com.example.Ehpad.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.example.Ehpad.dto.PatientNoteDTO;
import com.example.Ehpad.entity.PatientNote;

import org.springframework.stereotype.Component;

@Component
public class PatientNoteMapper {
    
    public PatientNoteDTO toDTO(PatientNote entity) {
        if (entity == null) {
            return null;
        }
        
        return PatientNoteDTO.builder()
                .id(entity.getId())
                .patientId(entity.getPatient().getId())
                .auteurId(entity.getAuteurId())
                .contenu(entity.getContenu())
                .dateNote(entity.getDateNote())
                .important(entity.getImportant())
                .categorieNote(entity.getCategorieNote())
                .createdAt(entity.getCreatedAt())
                .build();
    }
    
    public PatientNote toEntity(PatientNoteDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return PatientNote.builder()
                .id(dto.getId())
                .auteurId(dto.getAuteurId())
                .contenu(dto.getContenu())
                .dateNote(dto.getDateNote())
                .important(dto.getImportant())
                .categorieNote(dto.getCategorieNote())
                .build();
    }
    
    public List<PatientNoteDTO> toDTOList(List<PatientNote> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<PatientNote> toEntityList(List<PatientNoteDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
