package com.tucarbure.tucarbures.stations;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.tucarbure.tucarbures.stations.StationDB.stationDB;

@Component
public class StationsMapper {

    StationDB map(Station station){
        return stationDB()
                .id(UUID.randomUUID())
                .marque(station.getMarque())
                .adresse(station.getAdresse())
                .carburants(station.getCarburants())
                .updatedDate(LocalDateTime.now())
                .build();
    }

    StationDB map(UUID stationID, Station station){
        return stationDB()
                .id(stationID)
                .marque(station.getMarque())
                .adresse(station.getAdresse())
                .carburants(station.getCarburants())
                .updatedDate(LocalDateTime.now())
                .build();
    }

}
