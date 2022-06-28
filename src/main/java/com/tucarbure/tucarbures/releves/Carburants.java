package com.tucarbure.tucarbures.releves;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Carburants {

    private String[] listeCarburants;
    private List<Carburant> details;

}
