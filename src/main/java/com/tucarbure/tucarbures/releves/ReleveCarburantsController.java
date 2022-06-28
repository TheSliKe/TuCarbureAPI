package com.tucarbure.tucarbures.releves;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ReleveCarburantsController {

    @Autowired
    private HistoriqueReleveCarburantService historiqueReleveCarburantService;

    @GetMapping("/stations/{stationsId}/historique")
    Iterable<HistoriqueReleveCarburants> getHistorique(@PathVariable(value="stationsId") UUID stationsId, @RequestParam int nb) {
        return historiqueReleveCarburantService.getHistoriqueReleveCarburants(stationsId, nb);
    }

}
