package com.example.Ehpad.entity;

public enum AlerteType {
    DOUCHE_MANQUANTE("douche_manquante"),
    AS_SURCHARGE("as_surcharge"),
    PATIENT_ALERTE("patient_alerte"),
    AUTRE("autre");
    
    private final String value;
    
    AlerteType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}
