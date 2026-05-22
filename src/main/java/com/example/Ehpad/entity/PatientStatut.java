package com.example.Ehpad.entity;

public enum PatientStatut {
    HOSPITALISE("hospitalise"),
    AMBULATOIRE("ambulatoire"),
    CONGE("conge"),
    DECES("deces");
    
    private final String value;
    
    PatientStatut(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}
