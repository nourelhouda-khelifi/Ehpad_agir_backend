package com.example.Ehpad.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.example.Ehpad.dto.PatientHistoriqueDTO;
import com.example.Ehpad.entity.PatientHistorique;

import org.springframework.stereotype.Component;

@Component
public class PatientHistoriqueMapper {
    
    public PatientHistoriqueDTO toDTO(PatientHistorique entity) {
        if (entity == null) {
            return null;
        }
        
        return PatientHistoriqueDTO.builder()
                .id(entity.getId())
                .patientId(entity.getPatient().getId())
                .acteurId(entity.getActeurId())
                .typeAction(entity.getTypeAction())
                .description(entity.getDescription())
                .dateAction(entity.getDateAction())
                .createdAt(entity.getCreatedAt())
                .build();
    }
    
    public PatientHistorique toEntity(PatientHistoriqueDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return PatientHistorique.builder()
                .id(dto.getId())
                .acteurId(dto.getActeurId())
                .typeAction(dto.getTypeAction())
                .description(dto.getDescription())
                .dateAction(dto.getDateAction())
                .build();
    }
    
    public List<PatientHistoriqueDTO> toDTOList(List<PatientHistorique> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<PatientHistorique> toEntityList(List<PatientHistoriqueDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
