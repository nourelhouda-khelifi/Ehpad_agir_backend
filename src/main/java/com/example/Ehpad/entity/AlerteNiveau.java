package com.example.Ehpad.entity;

public enum AlerteNiveau {
    CRITIQUE("critique"),
    MOYEN("moyen"),
    BAS("bas");
    
    private final String value;
    
    AlerteNiveau(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}
