package com.tucarbure.tucarbures.stations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class StationsController {

    @Autowired
    private StationsRepository stationsRepository;

    @Autowired
    private StationsMapper stationsMapper;

    @GetMapping("/stations")
    Iterable<StationDB> getStations() {
        return stationsRepository.findAll();
    }

    @PostMapping(value = "/stations")
    String postStations(@RequestBody Station station) {
        stationsRepository.save(stationsMapper.map(station));
        return "ok";
    }

    @PutMapping("/stations/{stationsId}")
    String putStation(@PathVariable(value="stationsId") UUID stationsId, @RequestBody Station station){
        stationsRepository.save(stationsMapper.map(stationsId, station));
        return "ok";
    }

    @DeleteMapping("/stations/{stationsId}")
    String deleteStation(@PathVariable(value="stationsId") UUID stationsId){
        stationsRepository.deleteById(stationsId);
        return "ok";
    }

}
