package com.tucarbure.tucarbures.stations;

import com.tucarbure.tucarbures.stations.carburants.Carburants;
import com.tucarbure.tucarbures.stations.marques.Marque;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Station {

    private Marque marque;
    private Adresse adresse;
    private List<Carburants> carburants;

}
