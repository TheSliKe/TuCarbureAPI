package com.tucarbure.tucarbures;

import com.tucarbure.tucarbures.marques.Marque;
import com.tucarbure.tucarbures.marques.MarqueDB;
import com.tucarbure.tucarbures.marques.MarqueMapper;
import com.tucarbure.tucarbures.marques.MarquesRepository;
import com.tucarbure.tucarbures.releves.Carburants;
import com.tucarbure.tucarbures.releves.HistoriqueReleveCarburantsRepository;
import com.tucarbure.tucarbures.response.StationsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.tucarbure.tucarbures.releves.HistoriqueReleveCarburants.historiqueReleveCarburantsBuilder;

@Service
public class StationService {

    @Autowired
    private StationsRepository stationsRepository;
    @Autowired
    private HistoriqueReleveCarburantsRepository historiqueReleveCarburantsRepository;
    @Autowired
    private MarquesRepository marquesRepository;
    @Autowired
    private MarqueMapper marqueMapper;
    @Autowired
    private StationsMapper stationsMapper;

    StationsResponse getAllStationsInRange(double latitude, double longitude, int distance){

        double r_earth = 6378;

        double latitude1  = latitude  + (distance / r_earth) * (180 / Math.PI);
        double latitude2  = latitude  + (-distance / r_earth) * (180 / Math.PI);
        double longitude1 = longitude + (distance / r_earth) * (180 / Math.PI) / Math.cos(latitude * Math.PI/180);
        double longitude2 = longitude + (-distance / r_earth) * (180 / Math.PI) / Math.cos(latitude * Math.PI/180);

        List<StationDB> stationList = new ArrayList<>();
        stationsRepository.findAllByAdresse_LatitudeBetweenAndAdresse_LongitudeBetween(latitude2, latitude1, longitude2, longitude1).forEach(stationList::add);

        return StationsResponse.builder()
                .nbEnregistrement(stationList.size())
                .latitude(latitude)
                .longitude(longitude)
                .range(distance)
                .stations(stationList)
                .build();
    }

    StationDB getStation(UUID stationId){
        Optional<StationDB> optionalStationDB = stationsRepository.findById(stationId);
        return optionalStationDB.get();
    }

    Carburants getCarburantsStation(UUID stationId){

        Optional<StationDB> optionalStationDB = stationsRepository.findById(stationId);
        return optionalStationDB.map(StationDB::getCarburants).orElse(null);
    }

    void postCarburant(UUID stationId, Carburants carburants) {

        Optional<StationDB> optionalStationDB = stationsRepository.findById(stationId);

        if (optionalStationDB.isPresent()){
            StationDB stationDB = optionalStationDB.get();

            stationDB.setCarburants(carburants);

            historiqueReleveCarburantsRepository.save(historiqueReleveCarburantsBuilder()
                    .id(UUID.randomUUID())
                    .stationId(stationId)
                    .carburants(carburants)
                    .date(LocalDateTime.now())
                    .build()
            );

            stationsRepository.save(stationDB);

        }

    }

    void saveStation(Station station){

        Marque marqueFromStation = station.getMarque();
        Optional<MarqueDB> marqueDBOptional = marquesRepository.findByNomAndDescription(marqueFromStation.getNom(), marqueFromStation.getDescription());

        if (marqueDBOptional.isEmpty()){
            marquesRepository.save(marqueMapper.map(station.getMarque()));
        }

        saveStationDB(stationsMapper.map(station));
    }

    void updateSelectedStation(UUID stationId, Station station){

        System.out.println(station);

        historiqueReleveCarburantsRepository.save(historiqueReleveCarburantsBuilder()
                .id(UUID.randomUUID())
                .stationId(stationId)
                .carburants(station.getCarburants())
                .date(LocalDateTime.now())
                .build()
        );

        saveStationDB(stationsMapper.map(stationId, station));
    }

    void deleteMarque(UUID stationId){
        stationsRepository.deleteById(stationId);
    }

    private void saveStationDB(StationDB stationDB){
        stationsRepository.save(stationDB);
    }


}
