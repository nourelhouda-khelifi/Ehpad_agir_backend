package com.example.Ehpad.service;

import java.util.List;

import com.example.Ehpad.dto.TypeSoinCreateDTO;
import com.example.Ehpad.dto.TypeSoinDTO;
import com.example.Ehpad.entity.TypeSoin;
import com.example.Ehpad.mapper.TypeSoinMapper;
import com.example.Ehpad.repository.TypeSoinRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class TypeSoinService {
    private final TypeSoinRepository typeSoinRepository;
    private final TypeSoinMapper typeSoinMapper;
    
    public TypeSoinService(TypeSoinRepository typeSoinRepository, TypeSoinMapper typeSoinMapper) {
        this.typeSoinRepository = typeSoinRepository;
        this.typeSoinMapper = typeSoinMapper;
    }
    
    public List<TypeSoinDTO> getAllTypesSoins() {
        log.info("Fetching all care types");
        return typeSoinMapper.toDTOList(typeSoinRepository.findAll());
    }
    
    public TypeSoinDTO getTypeSoinById(Long id) {
        log.info("Fetching care type with id: {}", id);
        TypeSoin typeSoin = typeSoinRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Care type not found with id: " + id));
        return typeSoinMapper.toDTO(typeSoin);
    }
    
    public TypeSoinDTO getTypeSoinByCode(String code) {
        log.info("Fetching care type by code: {}", code);
        TypeSoin typeSoin = typeSoinRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Care type not found with code: " + code));
        return typeSoinMapper.toDTO(typeSoin);
    }
    
    public TypeSoinDTO createTypeSoin(TypeSoinCreateDTO typeSoinDTO) {
        log.info("Creating care type: {}", typeSoinDTO.getCode());
        TypeSoin typeSoin = typeSoinMapper.toEntity(typeSoinDTO);
        TypeSoin savedTypeSoin = typeSoinRepository.save(typeSoin);
        return typeSoinMapper.toDTO(savedTypeSoin);
    }
    
    public TypeSoinDTO updateTypeSoin(Long id, TypeSoinDTO typeSoinDTO) {
        log.info("Updating care type with id: {}", id);
        TypeSoin typeSoin = typeSoinRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Care type not found with id: " + id));
        
        typeSoin.setCode(typeSoinDTO.getCode());
        typeSoin.setLibelle(typeSoinDTO.getLibelle());
        typeSoin.setDureeParDefaut(typeSoinDTO.getDureeParDefaut());
        typeSoin.setActif(typeSoinDTO.getActif());
        
        TypeSoin updatedTypeSoin = typeSoinRepository.save(typeSoin);
        return typeSoinMapper.toDTO(updatedTypeSoin);
    }
    
    public void deleteTypeSoin(Long id) {
        log.info("Deleting care type with id: {}", id);
        if (!typeSoinRepository.existsById(id)) {
            throw new EntityNotFoundException("Care type not found with id: " + id);
        }
        typeSoinRepository.deleteById(id);
    }
}
