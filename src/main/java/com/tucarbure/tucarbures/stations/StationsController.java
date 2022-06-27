package com.tucarbure.tucarbures.stations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class StationsController {

    @Autowired
    private StationService stationService;

    @GetMapping("/stations")
    Iterable<StationDB> getStations() {
        return stationService.getAllStation();
    }

    @PostMapping(value = "/stations")
    String postStations(@RequestBody Station station) {
        stationService.saveStation(station);
        return "ok";
    }

    @PutMapping("/stations/{stationsId}")
    String putStation(@PathVariable(value="stationsId") UUID stationsId, @RequestBody Station station){
        stationService.updateSelectedStation(stationsId, station);
        return "ok";
    }

    @DeleteMapping("/stations/{stationsId}")
    String deleteStation(@PathVariable(value="stationsId") UUID stationsId){
        stationService.deleteMarque(stationsId);
        return "ok";
    }

}
