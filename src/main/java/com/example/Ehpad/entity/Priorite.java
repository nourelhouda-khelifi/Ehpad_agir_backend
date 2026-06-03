package com.example.Ehpad.entity;

public enum Priorite {
    NON_DEFINI("NON_DEFINI"),
    BASSE("BASSE"),
    NORMALE("NORMALE"),
    HAUTE("HAUTE"),
    CRITIQUE("CRITIQUE");
    
    private final String value;
    
    Priorite(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}
