package com.tucarbure.tucarbures;

import com.tucarbure.tucarbures.releves.Carburant;
import com.tucarbure.tucarbures.marques.Marque;
import com.tucarbure.tucarbures.releves.Carburants;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Station {

    private Marque marque;
    private Adresse adresse;
    private Carburants carburants;

}
