package com.example.Ehpad.entity;

public enum PatientCategorie {
    CAT1("cat1"),
    CAT2("cat2"),
    CAT3("cat3"),
    CAT4("cat4");
    
    private final String value;
    
    PatientCategorie(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}
