package com.tucarbure.tucarbures.releves;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReleveCarburants {

    private String nom;
    private String codeEuropeen;
    private boolean disponible;
    private LocalDateTime lastUpdate;
    private double prix;

    public static ReleveCarburants.ReleveCarburantsBuilder carburants(){
        return ReleveCarburants.builder();
    }
}
