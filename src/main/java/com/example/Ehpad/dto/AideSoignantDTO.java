package com.example.Ehpad.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AideSoignantDTO {
    private Long id;
    private String code;
    private String secteur;
    private String color;
    private Boolean actif;
}
