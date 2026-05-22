package com.example.Ehpad.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.example.Ehpad.dto.TypeSoinDTO;
import com.example.Ehpad.entity.TypeSoin;

import org.springframework.stereotype.Component;

@Component
public class TypeSoinMapper {
    
    public TypeSoinDTO toDTO(TypeSoin entity) {
        if (entity == null) {
            return null;
        }
        
        return TypeSoinDTO.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .libelle(entity.getLibelle())
                .dureeParDefaut(entity.getDureeParDefaut())
                .actif(entity.getActif())
                .build();
    }
    
    public TypeSoin toEntity(TypeSoinDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return TypeSoin.builder()
                .id(dto.getId())
                .code(dto.getCode())
                .libelle(dto.getLibelle())
                .dureeParDefaut(dto.getDureeParDefaut())
                .actif(dto.getActif())
                .build();
    }
    
    public List<TypeSoinDTO> toDTOList(List<TypeSoin> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<TypeSoin> toEntityList(List<TypeSoinDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
