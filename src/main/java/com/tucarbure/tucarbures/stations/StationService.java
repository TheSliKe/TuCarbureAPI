package com.tucarbure.tucarbures.stations;

import com.tucarbure.tucarbures.stations.marques.Marque;
import com.tucarbure.tucarbures.stations.marques.MarqueDB;
import com.tucarbure.tucarbures.stations.marques.MarqueMapper;
import com.tucarbure.tucarbures.stations.marques.MarquesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    Iterable<StationDB> getAllStation(){
        return stationsRepository.findAll();
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
