package com.tucarbure.tucarbures;

import com.tucarbure.tucarbures.releves.ReleveCarburants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class StationsController {

    @Autowired
    private StationService stationService;

    @GetMapping("/stations")
    Iterable<StationDB> getStations() {
        return stationService.getAllStations();
    }

    @GetMapping("/stations/{stationsId}")
    StationDB getStation(@PathVariable(value="stationsId") UUID stationsId) {
        return stationService.getStation(stationsId);
    }

    @GetMapping("/stations/{stationsId}/carburants")
    Iterable<ReleveCarburants> getCarburantsStation(@PathVariable(value="stationsId") UUID stationsId) {
        return stationService.getCarburantsStation(stationsId);
    }

    @PostMapping("/stations")
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

//    @GetMapping("/test")
//    String test() {
//
//        final String uri = "https://public.opendatasoft.com/api/records/1.0/search/?dataset=prix-des-carburants-j-1&q=&rows=10000";
//
//        RestTemplate restTemplate = new RestTemplate();
//        String result = restTemplate.getForObject(uri, String.class);
//
//        JSONObject jsonObject = new JSONObject(result);
//
//        JSONArray jsonArray = (JSONArray) jsonObject.get("records");
//
//        jsonArray.forEach(item -> {
//
//            JSONObject obj = (JSONObject) item;
//            JSONObject fields = (JSONObject) obj.get("fields");
//
//            if (obj.has("geometry")){
//                JSONObject geometry = (JSONObject) obj.get("geometry");
//
//                if (fields.has("name") && fields.has("address") && fields.has("cp") && fields.has("com_arm_name")){
//                    System.out.println("save");
//
//                    if (fields.has("fuel")){
//                        List<ReleveCarburants> list = new ArrayList<>();
//                        String[] arrSplit = fields.getString("fuel").split("/");
//                        for (int i = 0; i < arrSplit.length; i++) {
//                            list.add(ReleveCarburants.builder()
//                                            .nom(arrSplit[i])
//                                            .disponible(true)
//                                    .build());
//                        }
//
//                        stationService.saveStation(Station.builder()
//                                .marque(Marque.builder()
//                                        .nom(fields.getString("name"))
//                                        .description(fields.getString("name")).build())
//                                .adresse(Adresse.builder()
//                                        .rue(fields.getString("address"))
//                                        .codePostal(fields.getString("cp"))
//                                        .ville(fields.getString("com_arm_name"))
//                                        .latitude(geometry.getJSONArray("coordinates").getDouble(1))
//                                        .longitude(geometry.getJSONArray("coordinates").getDouble(0))
//                                        .build())
//                                .carburants(list)
//                                .build()
//                        );
//
//                    }
//
//                }
//
//            }
//
//        });
//
//        return "ok";
//    }


}
