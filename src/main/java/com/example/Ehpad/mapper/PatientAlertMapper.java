package com.example.Ehpad.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.example.Ehpad.dto.PatientAlertDTO;
import com.example.Ehpad.entity.PatientAlert;

import org.springframework.stereotype.Component;

@Component
public class PatientAlertMapper {
    
    public PatientAlertDTO toDTO(PatientAlert entity) {
        if (entity == null) {
            return null;
        }
        
        return PatientAlertDTO.builder()
                .id(entity.getId())
                .patientId(entity.getPatient().getId())
                .aideSoignantId(entity.getAideSoignant() != null ? entity.getAideSoignant().getId() : null)
                .type(entity.getType())
                .niveau(entity.getNiveau())
                .message(entity.getMessage())
                .resolue(entity.getResolue())
                .dateCreation(entity.getDateCreation())
                .dateResolution(entity.getDateResolution())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
    
    public PatientAlert toEntity(PatientAlertDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return PatientAlert.builder()
                .id(dto.getId())
                .type(dto.getType())
                .niveau(dto.getNiveau())
                .message(dto.getMessage())
                .resolue(dto.getResolue())
                .dateCreation(dto.getDateCreation())
                .dateResolution(dto.getDateResolution())
                .build();
    }
    
    public List<PatientAlertDTO> toDTOList(List<PatientAlert> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<PatientAlert> toEntityList(List<PatientAlertDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
