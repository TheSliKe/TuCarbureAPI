package com.tucarbure.tucarbures.response;

import com.tucarbure.tucarbures.StationDB;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StationsResponse {

    private int nbEnregistrement;
    private double latitude;
    private double longitude;
    private double range;
    private Iterable<StationDB> stations;


}
