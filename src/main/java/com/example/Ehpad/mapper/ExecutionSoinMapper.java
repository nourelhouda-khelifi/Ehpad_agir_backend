package com.example.Ehpad.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.example.Ehpad.dto.ExecutionSoinCreateDTO;
import com.example.Ehpad.dto.ExecutionSoinDTO;
import com.example.Ehpad.entity.ExecutionSoin;
import com.example.Ehpad.entity.PlanningSoin;

import org.springframework.stereotype.Component;

@Component
public class ExecutionSoinMapper {
    private final PatientMapper patientMapper;
    private final TypeSoinMapper typeSoinMapper;
    private final AideSoignantMapper aideSoignantMapper;
    
    public ExecutionSoinMapper(PatientMapper patientMapper, TypeSoinMapper typeSoinMapper,
            AideSoignantMapper aideSoignantMapper) {
        this.patientMapper = patientMapper;
        this.typeSoinMapper = typeSoinMapper;
        this.aideSoignantMapper = aideSoignantMapper;
    }
    
    public ExecutionSoinDTO toDTO(ExecutionSoin entity) {
        if (entity == null) {
            return null;
        }
        
        return ExecutionSoinDTO.builder()
                .id(entity.getId())
                .planningSoinId(entity.getPlanningSoin() != null ? entity.getPlanningSoin().getId() : null)
                .patientId(entity.getPatient().getId())
                .typeSoinId(entity.getTypeSoin().getId())
                .aideSoignantId(entity.getAideSoignant().getId())
                .dateExecution(entity.getDateExecution())
                .heureExecution(entity.getHeureExecution())
                .statut(entity.getStatut())
                .commentaire(entity.getCommentaire())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .patient(patientMapper.toDTO(entity.getPatient()))
                .typeSoin(typeSoinMapper.toDTO(entity.getTypeSoin()))
                .aideSoignant(aideSoignantMapper.toDTO(entity.getAideSoignant()))
                .build();
    }
    
    public ExecutionSoin toEntity(ExecutionSoinDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return ExecutionSoin.builder()
                .id(dto.getId())
                .dateExecution(dto.getDateExecution())
                .heureExecution(dto.getHeureExecution())
                .statut(dto.getStatut())
                .commentaire(dto.getCommentaire())
                .build();
    }

    public ExecutionSoin toEntity(ExecutionSoinCreateDTO dto) {
        if (dto == null) {
            return null;
        }

        return ExecutionSoin.builder()
                .planningSoin(dto.getPlanningSoinId() != null ? PlanningSoin.builder().id(dto.getPlanningSoinId()).build() : null)
                .dateExecution(dto.getDateExecution())
                .heureExecution(dto.getHeureExecution())
                .statut(dto.getStatut())
                .commentaire(dto.getCommentaire())
                .build();
    }
    
    public List<ExecutionSoinDTO> toDTOList(List<ExecutionSoin> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<ExecutionSoin> toEntityList(List<ExecutionSoinDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
