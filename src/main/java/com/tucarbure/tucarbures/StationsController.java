package com.tucarbure.tucarbures;

import com.tucarbure.tucarbures.marques.Marque;
import com.tucarbure.tucarbures.releves.Carburant;
import com.tucarbure.tucarbures.releves.Carburants;
import com.tucarbure.tucarbures.response.StationsResponse;
import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class StationsController {

    @Autowired
    private StationService stationService;

    public final static Map<String, String> CARBURANTS = new HashMap<String, String>()
    {{
        put("SP95", "E5");
        put("SP98", "E5");
        put("SP95-E10", "E10");
        put("Super Ethanol", "E85");
        put("Gazole", "B7");
        put("Gazole EMAG", "B10");
        put("Gazole Paraffinique", "XTL");
        put("Hydrogène", "H2");
        put("GPL-C", "LPG");
        put("Gaz Naturel Comprimé", "GNC");
        put("Gaz Naturel Liquéfié", "GNL");
    }};

    @GetMapping("/stations")
    ResponseEntity<StationsResponse> getStations(@RequestParam double latitude, @RequestParam double longitude, @RequestParam(defaultValue = "20", required = false) Integer distance, @RequestParam(defaultValue = "", required = false) String[] codeEuropeen) {
        return ok(stationService.getAllStationsInRange(latitude, longitude, distance, codeEuropeen));
    }

    @GetMapping("/stations/{stationsId}")
    ResponseEntity<?> getStation(@PathVariable(value="stationsId") UUID stationsId) {
        return stationService.getStation(stationsId);
    }

    @PostMapping("/stations")
    ResponseEntity<?> postStations(@RequestBody Station station) {
        return stationService.saveStation(station);
    }

    @GetMapping("/stations/{stationsId}/carburants")
    ResponseEntity<?> getCarburantsStation(@PathVariable(value="stationsId") UUID stationsId) {
        return stationService.getCarburantsStation(stationsId);
    }

    @PostMapping("/stations/{stationsId}/carburants")
    ResponseEntity<?> postCarburantsStation(@RequestHeader("Authorization") String Authorization, @PathVariable(value="stationsId") UUID stationsId, @RequestBody Carburants carburants) {
        return stationService.postCarburant(stationsId, carburants, Authorization);
    }

    @PutMapping("/stations/{stationsId}")
    ResponseEntity<?> putStation(@RequestHeader("Authorization") String Authorization, @PathVariable(value="stationsId") UUID stationsId, @RequestBody Station station){
        return stationService.updateSelectedStation(stationsId, station, Authorization);
    }

    @DeleteMapping("/stations/{stationsId}")
    String deleteStation(@PathVariable(value="stationsId") UUID stationsId){
        stationService.deleteMarque(stationsId);
        return "ok";
    }

}
