package com.tucarbure.tucarbures;

import com.tucarbure.tucarbures.marques.Marque;
import com.tucarbure.tucarbures.marques.MarqueDB;
import com.tucarbure.tucarbures.marques.MarqueMapper;
import com.tucarbure.tucarbures.marques.MarquesRepository;
import com.tucarbure.tucarbures.releves.Carburants;
import com.tucarbure.tucarbures.releves.HistoriqueReleveCarburants;
import com.tucarbure.tucarbures.releves.HistoriqueReleveCarburantsRepository;
import com.tucarbure.tucarbures.response.StationsResponse;
import com.tucarbure.tucarbures.security.JwtTokenProvider;
import com.tucarbure.tucarbures.security.User;
import com.tucarbure.tucarbures.security.UserRepository;
import com.tucarbure.tucarbures.security.usermanagement.UserProfilDB;
import com.tucarbure.tucarbures.security.usermanagement.UserProfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static com.tucarbure.tucarbures.releves.HistoriqueReleveCarburants.historiqueReleveCarburantsBuilder;
import static com.tucarbure.tucarbures.response.GenericErrorResponse.genericErrorResponseBuilder;

@Service
public class StationService {

    @Autowired
    private StationsRepository stationsRepository;
    @Autowired
    private HistoriqueReleveCarburantsRepository historiqueReleveCarburantsRepository;
    @Autowired
    private MarquesRepository marquesRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfilRepository userProfilRepository;
    @Autowired
    private MarqueMapper marqueMapper;
    @Autowired
    private StationsMapper stationsMapper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    StationsResponse getAllStationsInRange(double latitude, double longitude, int distance, String[] code){

        double r_earth = 6378;

        double latitude1  = latitude  + (distance / r_earth) * (180 / Math.PI);
        double latitude2  = latitude  + (-distance / r_earth) * (180 / Math.PI);
        double longitude1 = longitude + (distance / r_earth) * (180 / Math.PI) / Math.cos(latitude * Math.PI/180);
        double longitude2 = longitude + (-distance / r_earth) * (180 / Math.PI) / Math.cos(latitude * Math.PI/180);

        List<StationDB> stationList = new ArrayList<>();
        stationsRepository.findAllByAdresse_LatitudeBetweenAndAdresse_LongitudeBetween(latitude2, latitude1, longitude2, longitude1).forEach(stationList::add);

        List<StationDB> stationDBListFinal = new ArrayList<>();

        if (code.length > 0){

            for (StationDB station : stationList) {

                for (String s : code) {

                    if (Arrays.asList(station.getCarburants().getListeCarburants()).contains(s) && !stationDBListFinal.contains(station)) {
                        stationDBListFinal.add(station);
                    }

                }

            }

            return StationsResponse.builder()
                    .nbEnregistrement(stationDBListFinal.size())
                    .latitude(latitude)
                    .longitude(longitude)
                    .range(distance)
                    .stations(stationDBListFinal)
                    .build();

        } else {

            return StationsResponse.builder()
                .nbEnregistrement(stationList.size())
                .latitude(latitude)
                .longitude(longitude)
                .range(distance)
                .stations(stationList)
                .build();
        }

    }

    ResponseEntity<?> getStation(UUID stationId){
        Optional<StationDB> optionalStationDB = stationsRepository.findById(stationId);

        if (optionalStationDB.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(genericErrorResponseBuilder().code(404).message("UUID not found").build());
        }

        return ResponseEntity.ok(optionalStationDB.get());
    }

    ResponseEntity<?> getCarburantsStation(UUID stationId){
        Optional<StationDB> optionalStationDB = stationsRepository.findById(stationId);


        if (optionalStationDB.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(genericErrorResponseBuilder().code(404).message("UUID not found").build());
        }

        return ResponseEntity.ok(optionalStationDB.map(StationDB::getCarburants));

    }

    ResponseEntity<?> postCarburant(UUID stationId, Carburants carburants, String Authorization) {


        String username = userRepository.findByEmail(jwtTokenProvider.getUsername(Authorization.substring(7))).getUsername();

        Optional<StationDB> optionalStationDB = stationsRepository.findById(stationId);

        if (optionalStationDB.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(genericErrorResponseBuilder().code(404).message("UUID not found").build());
        }

        StationDB stationDB = optionalStationDB.get();

        stationDB.setCarburants(carburants);

        HistoriqueReleveCarburants historique = historiqueReleveCarburantsBuilder()
                .id(UUID.randomUUID())
                .stationId(stationId)
                .carburants(carburants)
                .date(LocalDateTime.now())
                .username(username)
                .build();
        historiqueReleveCarburantsRepository.save(historique);

        UserProfilDB userProfilDB = userProfilRepository.findById(username).get();


        if (userProfilDB.getReleveIds() == null){
            userProfilDB.setReleveIds(new ArrayList<>());
            userProfilDB.getReleveIds().add(historique.getId());
        } else {
            userProfilDB.getReleveIds().add(historique.getId());
        }

        userProfilRepository.save(userProfilDB);


        stationsRepository.save(stationDB);

        return ResponseEntity.status(HttpStatus.CREATED).body(genericErrorResponseBuilder().code(201).message("Carburants saved").build());


    }

    ResponseEntity<?> saveStation(Station station){

        Marque marqueFromStation = station.getMarque();
        Optional<MarqueDB> marqueDBOptional = marquesRepository.findByNomAndDescription(marqueFromStation.getNom(), marqueFromStation.getDescription());

        if (marqueDBOptional.isEmpty()){
            marquesRepository.save(marqueMapper.map(station.getMarque()));
        }

        StationDB stationDB = stationsMapper.map(station);
        saveStationDB(stationDB);

        return ResponseEntity.status(HttpStatus.CREATED).body(genericErrorResponseBuilder().code(201).message("Station created with UUID : " + stationDB.getId()).build());
    }

    ResponseEntity<?> updateSelectedStation(UUID stationId, Station station, String Authorization){

        String username = userRepository.findByEmail(jwtTokenProvider.getUsername(Authorization.substring(7))).getUsername();

        HistoriqueReleveCarburants historique = historiqueReleveCarburantsBuilder()
                .id(UUID.randomUUID())
                .stationId(stationId)
                .carburants(station.getCarburants())
                .date(LocalDateTime.now())
                .username(username)
                .build();
        historiqueReleveCarburantsRepository.save(historique);

        UserProfilDB userProfilDB = userProfilRepository.findById(username).get();
        userProfilDB.getReleveIds().add(historique.getId());
        userProfilRepository.save(userProfilDB);


        saveStationDB(stationsMapper.map(stationId, station));

        return ResponseEntity.status(HttpStatus.OK).body(genericErrorResponseBuilder().code(200).message("Station updated with UUID : " + stationId).build());
    }

    void deleteMarque(UUID stationId){
        stationsRepository.deleteById(stationId);
    }

    public long countStation(){
        return stationsRepository.count();
    }

    private void saveStationDB(StationDB stationDB){
        stationsRepository.save(stationDB);
    }

}
