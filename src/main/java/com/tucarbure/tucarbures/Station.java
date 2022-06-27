package com.tucarbure.tucarbures;

import com.tucarbure.tucarbures.releves.ReleveCarburants;
import com.tucarbure.tucarbures.marques.Marque;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Station {

    private Marque marque;
    private Adresse adresse;
    private List<ReleveCarburants> carburants;

}
