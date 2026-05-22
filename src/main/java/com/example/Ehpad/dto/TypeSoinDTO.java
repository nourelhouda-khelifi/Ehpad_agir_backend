package com.example.Ehpad.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeSoinDTO {
    private Long id;
    private String code;
    private String libelle;
    private Integer dureeParDefaut;
    private Boolean actif;
}
