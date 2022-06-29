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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class StationsController {

    @Autowired
    private StationService stationService;

    @GetMapping("/stations")
    ResponseEntity<StationsResponse> getStations(@RequestParam double latitude, @RequestParam double longitude, @RequestParam(defaultValue = "20", required = false) Integer distance) {
        return ok(stationService.getAllStationsInRange(latitude, longitude, distance));
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
    ResponseEntity<?> postCarburantsStation(@PathVariable(value="stationsId") UUID stationsId, @RequestBody Carburants carburants) {
        return stationService.postCarburant(stationsId, carburants);
    }

    @PutMapping("/stations/{stationsId}")
    ResponseEntity<?> putStation(@PathVariable(value="stationsId") UUID stationsId, @RequestBody Station station){
        return stationService.updateSelectedStation(stationsId, station);
    }

    @DeleteMapping("/stations/{stationsId}")
    String deleteStation(@PathVariable(value="stationsId") UUID stationsId){
        stationService.deleteMarque(stationsId);
        return "ok";
    }

    @GetMapping("/test")
    String test() {

        final String uri = "https://public.opendatasoft.com/api/records/1.0/search/?dataset=prix-des-carburants-j-1&q=&rows=10000";

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        JSONObject jsonObject = new JSONObject(result);

        JSONArray jsonArray = (JSONArray) jsonObject.get("records");

        jsonArray.forEach(item -> {

            JSONObject obj = (JSONObject) item;
            JSONObject fields = (JSONObject) obj.get("fields");

            if (obj.has("geometry")){
                JSONObject geometry = (JSONObject) obj.get("geometry");

                if (fields.has("name") && fields.has("address") && fields.has("cp") && fields.has("com_arm_name")){
                    System.out.println("save" + fields.getString("name"));

                    if (fields.has("fuel")){

                        List<Carburant> list = new ArrayList<>();
                        String[] fuel = fields.getString("fuel").split("/");
                        for (int i = 0; i < fuel.length; i++) {
                            list.add(Carburant.builder()
                                            .nom(fuel[i])
                                            .codeEuropeen(fuel[i])
                                            .disponible(true)
                                    .build()
                            );
                        }

                        String[] shortage = new String[0];
                        if (fields.has("shortage")){
                            shortage = fields.getString("shortage").split("/");

                            for (int i = 0; i < shortage.length; i++) {
                                list.add(Carburant.builder()
                                        .nom(shortage[i])
                                        .codeEuropeen(shortage[i])
                                        .disponible(false)
                                        .build()
                                );
                            }
                        }

                        Carburants carburants = Carburants.builder()
                                .listeCarburants(ArrayUtils.addAll(fuel, shortage))
                                .details(list)
                                .build();

                        stationService.saveStation(Station.builder()
                                .marque(Marque.builder()
                                        .nom(fields.getString("name"))
                                        .description(fields.getString("name")).build())
                                .adresse(Adresse.builder()
                                        .rue(fields.getString("address"))
                                        .codePostal(fields.getString("cp"))
                                        .ville(fields.getString("com_arm_name"))
                                        .latitude(geometry.getJSONArray("coordinates").getDouble(1))
                                        .longitude(geometry.getJSONArray("coordinates").getDouble(0))
                                        .build())
                                .carburants(carburants)
                                .build()
                        );

                    }

                }

            }

        });

        return "ok";
    }


}
