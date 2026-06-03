package com.example.Ehpad.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.example.Ehpad.dto.PatientCreateDTO;
import com.example.Ehpad.dto.PatientDTO;
import com.example.Ehpad.entity.Patient;

import org.springframework.stereotype.Component;

@Component
public class PatientMapper {

    public PatientDTO toDTO(Patient entity) {
        if (entity == null) {
            return null;
        }

        return PatientDTO.builder()
                .id(entity.getId())
                .numeroChambre(entity.getNumeroChambre())
                .nom(entity.getNom())
                .prenom(entity.getPrenom())
                .etage(entity.getEtage())
                .statut(entity.getStatut())
                .categorie(entity.getCategorie())
                .profil(entity.getProfil())
                .priorite(entity.getPriorite())
                .tempsToiletteLit(entity.getTempsToiletteLit())
                .tempsToiletteVasque(entity.getTempsToiletteVasque())
                .tempsToiletteMoyen(entity.getTempsToiletteMoyen())
                .tempsWcMoyen(entity.getTempsWcMoyen())
                .tempsCoucherMoyen(entity.getTempsCoucherMoyen())
                .aideSoignant(entity.getAideSoignant())
                .petitDejeunerAide(entity.getPetitDejeunerAide())
                .sansDouche(entity.getSansDouche())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public Patient toEntity(PatientDTO dto) {
        if (dto == null) {
            return null;
        }

        return Patient.builder()
                .id(dto.getId())
                .numeroChambre(dto.getNumeroChambre())
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .etage(dto.getEtage())
                .statut(dto.getStatut())
                .categorie(dto.getCategorie())
                .profil(dto.getProfil())
                .priorite(dto.getPriorite())
                .tempsToiletteLit(dto.getTempsToiletteLit())
                .tempsToiletteVasque(dto.getTempsToiletteVasque())
                .tempsToiletteMoyen(dto.getTempsToiletteMoyen())
                .tempsWcMoyen(dto.getTempsWcMoyen())
                .tempsCoucherMoyen(dto.getTempsCoucherMoyen())
                .aideSoignant(dto.getAideSoignant())
                .petitDejeunerAide(dto.getPetitDejeunerAide())
                .sansDouche(dto.getSansDouche())
                .build();
    }

    public Patient toEntity(PatientCreateDTO dto) {
        if (dto == null) {
            return null;
        }

        return Patient.builder()
                .numeroChambre(dto.getNumeroChambre())
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .etage(dto.getEtage())
                .statut(dto.getStatut())
                .categorie(dto.getCategorie())
                .profil(dto.getProfil())
                .priorite(dto.getPriorite())
                .tempsToiletteLit(dto.getTempsToiletteLit())
                .tempsToiletteVasque(dto.getTempsToiletteVasque())
                .tempsToiletteMoyen(dto.getTempsToiletteMoyen())
                .tempsWcMoyen(dto.getTempsWcMoyen())
                .tempsCoucherMoyen(dto.getTempsCoucherMoyen())
                .aideSoignant(dto.getAideSoignant())
                .petitDejeunerAide(dto.getPetitDejeunerAide())
                .sansDouche(dto.getSansDouche())
                .build();
    }

    public List<PatientDTO> toDTOList(List<Patient> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Patient> toEntityList(List<PatientDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
