package com.tucarbure.tucarbures;

import com.tucarbure.tucarbures.marques.Marque;
import com.tucarbure.tucarbures.marques.MarqueDB;
import com.tucarbure.tucarbures.marques.MarqueMapper;
import com.tucarbure.tucarbures.marques.MarquesRepository;
import com.tucarbure.tucarbures.releves.ReleveCarburants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StationService {

    @Autowired
    private StationsRepository stationsRepository;
    @Autowired
    private MarquesRepository marquesRepository;
    @Autowired
    private MarqueMapper marqueMapper;
    @Autowired
    private StationsMapper stationsMapper;

    Iterable<StationDB> getAllStationsInRange(double latitude, double longitude, int distance){

        double r_earth = 6378;

        double latitude1  = latitude  + (distance / r_earth) * (180 / Math.PI);
        double latitude2  = latitude  + (-distance / r_earth) * (180 / Math.PI);
        double longitude1 = longitude + (distance / r_earth) * (180 / Math.PI) / Math.cos(latitude * Math.PI/180);
        double longitude2 = longitude + (-distance / r_earth) * (180 / Math.PI) / Math.cos(latitude * Math.PI/180);

        return stationsRepository.findAllByAdresse_LatitudeBetweenAndAdresse_LongitudeBetween(latitude2, latitude1, longitude2, longitude1);
    }

    StationDB getStation(UUID stationId){
        Optional<StationDB> optionalStationDB = stationsRepository.findById(stationId);
        return optionalStationDB.get();
    }

    Iterable<ReleveCarburants> getCarburantsStation(UUID stationId){

        Optional<StationDB> optionalStationDB = stationsRepository.findById(stationId);
        if (optionalStationDB.isPresent()){
            return optionalStationDB.get().getReleveCarburants();
        }
        return List.of();
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
        saveStationDB(stationsMapper.map(stationId, station));
    }

    void deleteMarque(UUID stationId){
        stationsRepository.deleteById(stationId);
    }

    private void saveStationDB(StationDB stationDB){
        stationsRepository.save(stationDB);
    }

}