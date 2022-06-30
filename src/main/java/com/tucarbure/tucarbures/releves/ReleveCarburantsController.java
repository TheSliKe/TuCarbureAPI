package com.tucarbure.tucarbures.releves;

import com.tucarbure.tucarbures.releves.code.CodeCarburants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.tucarbure.tucarbures.releves.code.CodeCarburants.codeCarburantsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
public class ReleveCarburantsController {

    @Autowired
    private HistoriqueReleveCarburantService historiqueReleveCarburantService;

    @GetMapping("/stations/{stationsId}/historique")
    @Operation(summary = "historisque d'une station service", description = "Lors de l'appel, on retourne un JSON de la station service avec l'historique des prix carburants", responses = {
            @ApiResponse(responseCode = "200", description = "JSON ok"),
            @ApiResponse(responseCode = "400", description = "id station inexistant")
    })
    Iterable<HistoriqueReleveCarburants> getHistorique(@PathVariable(value = "stationsId") UUID stationsId,
            @RequestParam int nb) {
        return historiqueReleveCarburantService.getHistoriqueReleveCarburants(stationsId, nb);
    }

    @GetMapping("/historique/{historiqueId}")
    HistoriqueReleveCarburants getHistoriqueGiven(@PathVariable(value="historiqueId") UUID historiqueId) {
        return historiqueReleveCarburantService.getHistorique(historiqueId);
    }

}
