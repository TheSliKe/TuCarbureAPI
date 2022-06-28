package com.tucarbure.tucarbures.releves;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Carburant {

    private String nom;
    private String codeEuropeen;
    private boolean disponible;
    private double prix;

    public static Carburant.CarburantBuilder carburants(){
        return Carburant.builder();
    }
}
