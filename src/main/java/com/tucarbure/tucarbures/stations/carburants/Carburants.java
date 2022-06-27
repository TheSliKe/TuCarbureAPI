package com.tucarbure.tucarbures.stations.carburants;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Carburants {

    private String nom;
    private String codeEuropeen;

    public static Carburants.CarburantsBuilder carburants(){
        return Carburants.builder();
    }
}
