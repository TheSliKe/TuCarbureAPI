package com.tucarbure.tucarbures.releves;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.tucarbure.tucarbures.releves.CodeCarburants.codeCarburantsBuilder;

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

    @GetMapping("/carburants")
    @Operation(summary = "Liste des carburants", description = "Lors de l'appel, on retourne un JSON de la liste des carburants", responses = {
            @ApiResponse(responseCode = "200", description = "JSON ok")
    })
    List<CodeCarburants> getcarburants() {

        List<CodeCarburants> codeCarburants = new ArrayList<CodeCarburants>() {
            {
                add(codeCarburantsBuilder().nom("SP95").code("E5").build());
                add(codeCarburantsBuilder().nom("SP98").code("E5").build());
                add(codeCarburantsBuilder().nom("SP95-E10").code("E10").build());
                add(codeCarburantsBuilder().nom("Super Ethanol").code("E85").build());
                add(codeCarburantsBuilder().nom("Gazole").code("B7").build());
                add(codeCarburantsBuilder().nom("Gazole EMAG").code("B10").build());
                add(codeCarburantsBuilder().nom("Gazole Paraffinique").code("XTL").build());
                add(codeCarburantsBuilder().nom("Hydrogène").code("H2").build());
                add(codeCarburantsBuilder().nom("GPL-C").code("LPG").build());
                add(codeCarburantsBuilder().nom("Gaz Naturel Comprimé").code("GNC").build());
                add(codeCarburantsBuilder().nom("Gaz Naturel Liquéfié").code("GNL").build());
            }
        };

        return codeCarburants;
    }

}
