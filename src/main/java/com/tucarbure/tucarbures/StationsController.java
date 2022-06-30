package com.tucarbure.tucarbures;

import com.tucarbure.tucarbures.marques.Marque;
import com.tucarbure.tucarbures.releves.Carburant;
import com.tucarbure.tucarbures.releves.Carburants;
import com.tucarbure.tucarbures.response.StationsResponse;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;

import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@OpenAPIDefinition(info = @Info(title = "Swagger TuCarbures", version = "1", description = "API pour les gestions des stations services relié à l'application TuCarbures"))
public class StationsController {

    @Autowired
    private StationService stationService;

    public final static Map<String, String> CARBURANTS = new HashMap<String, String>() {
        {
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
        }
    };

    @GetMapping("/stations")
    @Operation(summary = "Liste des stations services", description = "Lors de l'appel, on retourne une liste de stations service par le biais de la latitude, longitude et la distance souhaité ", responses = {
            @ApiResponse(responseCode = "200", description = "Liste des stations services avec trois paramètre : latitude, longitude et la distance"),
            @ApiResponse(responseCode = "400", description = "Deux paramètre obligatoire : la lattitude et la longitude")
    })
    ResponseEntity<StationsResponse> getStations(@RequestParam double latitude, @RequestParam double longitude,
            @RequestParam(defaultValue = "20", required = false) Integer distance) {
        return ok(stationService.getAllStationsInRange(latitude, longitude, distance));
    }

    @GetMapping("/stations/{stationsId}")
    @Operation(summary = "Informations sur une station services", description = "Lors de l'appel, on retourne les informations d'une stations service par le biais de son id", responses = {
            @ApiResponse(responseCode = "200", description = "JSON de la sation service"),
            @ApiResponse(responseCode = "404", description = "id inconnu"),
            @ApiResponse(responseCode = "400", description = "id non renseigné")
    })
    ResponseEntity<?> getStation(@PathVariable(value = "stationsId") UUID stationsId) {
        return stationService.getStation(stationsId);
    }

    @PostMapping("/stations")
    @Operation(summary = "Sauvegarde d'une station service", description = "Lors de l'appel, on enregistre en BDD les informations d'une stations service", responses = {
            @ApiResponse(responseCode = "201", description = "Document en BDD inserée"),
            @ApiResponse(responseCode = "401", description = "user "),
            @ApiResponse(responseCode = "400", description = "JSON vide"),
            @ApiResponse(responseCode = "500", description = "JSON invalide (erreur de champ renseignée")
    })
    ResponseEntity<?> postStations(@RequestBody Station station) {
        return stationService.saveStation(station);
    }

    @GetMapping("/stations/{stationsId}/carburants")
    @Operation(summary = "Informations sur les carburants d'une station", description = "Lors de l'appel, on retourne la liste des carburants d'une station service avec le détails des carburants", responses = {
            @ApiResponse(responseCode = "200", description = "liste de carburant d'une station service avec détail"),
            @ApiResponse(responseCode = "404", description = "id introuvable "),
            @ApiResponse(responseCode = "400", description = "id manquant")
    })
    ResponseEntity<?> getCarburantsStation(@PathVariable(value = "stationsId") UUID stationsId) {
        return stationService.getCarburantsStation(stationsId);
    }

    @PostMapping("/stations/{stationsId}/carburants")
    @Operation(summary = "Mise à jour des prix des carburants d'une station service", description = "Lors de l'appel, on enregistre en BDD histo les carburant puis on met à jour les nouveaux prix des carburants", responses = {
            @ApiResponse(responseCode = "201", description = "Enregistrement en BDD ok"),
            @ApiResponse(responseCode = "404", description = "id introuvable "),
            @ApiResponse(responseCode = "400", description = "id manquant")
    })
    ResponseEntity<?> postCarburantsStation(@PathVariable(value = "stationsId") UUID stationsId,
            @RequestBody Carburants carburants) {
        return stationService.postCarburant(stationsId, carburants);
    }

    @PutMapping("/stations/{stationsId}")
    @Operation(summary = "Modification d'une station service", description = "Lors de l'appel, on enregistre en BDD histo les carburant puis on met à jour les nouvelles informations de la station service", responses = {
            @ApiResponse(responseCode = "200", description = "Enregistrement en BDD ok"),
            @ApiResponse(responseCode = "400", description = "JSON vide")
    })
    ResponseEntity<?> putStation(@PathVariable(value = "stationsId") UUID stationsId, @RequestBody Station station) {
        return stationService.updateSelectedStation(stationsId, station);
    }

    @DeleteMapping("/stations/{stationsId}")
    @Operation(summary = "Suppression d'une station service", description = "Lors de l'appel, on supprime en BDD la station service", responses = {
            @ApiResponse(responseCode = "200", description = "suppression en BDD ok"),
            @ApiResponse(responseCode = "400", description = "Id introuvable")
    })
    String deleteStation(@PathVariable(value = "stationsId") UUID stationsId) {
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

            if (obj.has("geometry")) {
                JSONObject geometry = (JSONObject) obj.get("geometry");

                if (fields.has("name") && fields.has("address") && fields.has("cp") && fields.has("com_arm_name")) {
                    System.out.println("save" + fields.getString("name"));

                    if (fields.has("fuel")) {

                        List<Carburant> list = new ArrayList<>();
                        String[] fuel = fields.getString("fuel").split("/");
                        for (int i = 0; i < fuel.length; i++) {

                            String nomCarburant;
                            switch (fuel[i]) {
                                case "SP95" -> nomCarburant = "SP95";
                                case "SP98" -> nomCarburant = "SP98";
                                case "E10" -> nomCarburant = "SP95-E10";
                                case "E85" -> nomCarburant = "Super Ethanol";
                                case "Gazole" -> nomCarburant = "Gazole";
                                case "GPLc" -> nomCarburant = "GPL-C";
                                default -> {
                                    System.out.println(fuel[i]);
                                    nomCarburant = "oups";
                                }
                            }

                            list.add(Carburant.builder()
                                    .nom(nomCarburant)
                                    .codeEuropeen(CARBURANTS.get(nomCarburant))
                                    .disponible(true)
                                    .build());
                        }

                        String[] shortage = new String[0];
                        if (fields.has("shortage")) {
                            shortage = fields.getString("shortage").split("/");

                            for (int i = 0; i < shortage.length; i++) {

                                String nomCarburant;
                                switch (shortage[i]) {
                                    case "SP95" -> nomCarburant = "SP95";
                                    case "SP98" -> nomCarburant = "SP98";
                                    case "E10" -> nomCarburant = "SP95-E10";
                                    case "E85" -> nomCarburant = "Super Ethanol";
                                    case "Gazole" -> nomCarburant = "Gazole";
                                    case "GPLc" -> nomCarburant = "GPL-C";
                                    default -> {
                                        System.out.println(shortage[i]);
                                        nomCarburant = "oups";
                                    }
                                }

                                list.add(Carburant.builder()
                                        .nom(nomCarburant)
                                        .codeEuropeen(CARBURANTS.get(nomCarburant))
                                        .disponible(false)
                                        .build());
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
                                .build());

                    }

                }

            }

        });

        return "ok";
    }

}
