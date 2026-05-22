package com.example.Ehpad.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.example.Ehpad.dto.PlanningSoinDTO;
import com.example.Ehpad.entity.PlanningSoin;

import org.springframework.stereotype.Component;

@Component
public class PlanningSoinMapper {
    private final PatientMapper patientMapper;
    private final TypeSoinMapper typeSoinMapper;
    private final AideSoignantMapper aideSoignantMapper;
    
    public PlanningSoinMapper(PatientMapper patientMapper, TypeSoinMapper typeSoinMapper,
            AideSoignantMapper aideSoignantMapper) {
        this.patientMapper = patientMapper;
        this.typeSoinMapper = typeSoinMapper;
        this.aideSoignantMapper = aideSoignantMapper;
    }
    
    public PlanningSoinDTO toDTO(PlanningSoin entity) {
        if (entity == null) {
            return null;
        }
        
        return PlanningSoinDTO.builder()
                .id(entity.getId())
                .patientId(entity.getPatient().getId())
                .typeSoinId(entity.getTypeSoin().getId())
                .aideSoignantId(entity.getAideSoignant() != null ? entity.getAideSoignant().getId() : null)
                .datePrevue(entity.getDatePrevue())
                .jourSemaine(entity.getJourSemaine())
                .heurePrevue(entity.getHeurePrevue())
                .dureePrevue(entity.getDureePrevue())
                .moment(entity.getMoment())
                .statut(entity.getStatut())
                .commentaire(entity.getCommentaire())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .patient(patientMapper.toDTO(entity.getPatient()))
                .typeSoin(typeSoinMapper.toDTO(entity.getTypeSoin()))
                .aideSoignant(entity.getAideSoignant() != null ? aideSoignantMapper.toDTO(entity.getAideSoignant()) : null)
                .build();
    }
    
    public PlanningSoin toEntity(PlanningSoinDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return PlanningSoin.builder()
                .id(dto.getId())
                .datePrevue(dto.getDatePrevue())
                .jourSemaine(dto.getJourSemaine())
                .heurePrevue(dto.getHeurePrevue())
                .dureePrevue(dto.getDureePrevue())
                .moment(dto.getMoment())
                .statut(dto.getStatut())
                .commentaire(dto.getCommentaire())
                .build();
    }
    
    public List<PlanningSoinDTO> toDTOList(List<PlanningSoin> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<PlanningSoin> toEntityList(List<PlanningSoinDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
