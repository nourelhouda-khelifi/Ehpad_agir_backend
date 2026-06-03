package com.example.Ehpad.service;

import java.util.List;

import com.example.Ehpad.dto.AideSoignantCreateDTO;
import com.example.Ehpad.dto.AideSoignantDTO;
import com.example.Ehpad.entity.AideSoignant;
import com.example.Ehpad.mapper.AideSoignantMapper;
import com.example.Ehpad.repository.AideSoignantRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class AideSoignantService {
    private final AideSoignantRepository aideSoignantRepository;
    private final AideSoignantMapper aideSoignantMapper;

    public AideSoignantService(AideSoignantRepository aideSoignantRepository, AideSoignantMapper aideSoignantMapper) {
        this.aideSoignantRepository = aideSoignantRepository;
        this.aideSoignantMapper = aideSoignantMapper;
    }

    public List<AideSoignantDTO> getAllAidesSoignants() {
        log.info("Fetching all care staff members");
        return aideSoignantMapper.toDTOList(aideSoignantRepository.findAll());
    }

    public AideSoignantDTO getAideSoignantById(Long id) {
        log.info("Fetching care staff member with id: {}", id);
        AideSoignant aideSoignant = aideSoignantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Care staff not found with id: " + id));
        return aideSoignantMapper.toDTO(aideSoignant);
    }

    public AideSoignantDTO getAideSoignantByCode(String code) {
        log.info("Fetching care staff member by code: {}", code);
        AideSoignant aideSoignant = aideSoignantRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Care staff not found with code: " + code));
        return aideSoignantMapper.toDTO(aideSoignant);
    }

    public AideSoignantDTO createAideSoignant(AideSoignantCreateDTO aideSoignantDTO) {
        log.info("Creating care staff member: {}", aideSoignantDTO.getCode());
        AideSoignant aideSoignant = aideSoignantMapper.toEntity(aideSoignantDTO);
        AideSoignant savedAideSoignant = aideSoignantRepository.save(aideSoignant);
        return aideSoignantMapper.toDTO(savedAideSoignant);
    }

    public AideSoignantDTO updateAideSoignant(Long id, AideSoignantDTO aideSoignantDTO) {
        log.info("Updating care staff member with id: {}", id);
        AideSoignant aideSoignant = aideSoignantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Care staff not found with id: " + id));

        aideSoignant.setCode(aideSoignantDTO.getCode());
        aideSoignant.setColor(aideSoignantDTO.getColor());

        AideSoignant updatedAideSoignant = aideSoignantRepository.save(aideSoignant);
        return aideSoignantMapper.toDTO(updatedAideSoignant);
    }

    public void deleteAideSoignant(Long id) {
        log.info("Deleting care staff member with id: {}", id);
        if (!aideSoignantRepository.existsById(id)) {
            throw new EntityNotFoundException("Care staff not found with id: " + id);
        }
        aideSoignantRepository.deleteById(id);
    }
}
