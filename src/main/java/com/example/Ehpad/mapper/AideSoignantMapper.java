package com.example.Ehpad.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.example.Ehpad.dto.AideSoignantCreateDTO;
import com.example.Ehpad.dto.AideSoignantDTO;
import com.example.Ehpad.entity.AideSoignant;

import org.springframework.stereotype.Component;

@Component
public class AideSoignantMapper {
    
    public AideSoignantDTO toDTO(AideSoignant entity) {
        if (entity == null) {
            return null;
        }
        
        return AideSoignantDTO.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .color(entity.getColor())
                .build();
    }
    
    public AideSoignant toEntity(AideSoignantDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return AideSoignant.builder()
                .id(dto.getId())
                .code(dto.getCode())
                .color(dto.getColor())
                .build();
    }

    public AideSoignant toEntity(AideSoignantCreateDTO dto) {
        if (dto == null) {
            return null;
        }

        return AideSoignant.builder()
                .code(dto.getCode())
                .color(dto.getColor())
                .build();
    }
    
    public List<AideSoignantDTO> toDTOList(List<AideSoignant> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<AideSoignant> toEntityList(List<AideSoignantDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
