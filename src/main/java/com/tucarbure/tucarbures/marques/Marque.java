package com.tucarbure.tucarbures.marques;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Marque {

    private String nom;
    private String description;

}
