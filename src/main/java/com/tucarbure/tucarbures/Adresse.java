package com.tucarbure.tucarbures;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Adresse {

    private String rue;
    private String ville;
    private String codePostal;
    private double longitude;
    private double latitude;

    public static Adresse.AdresseBuilder adresse(){
        return Adresse.builder();
    }
}
