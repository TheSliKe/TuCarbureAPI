package com.tucarbure.tucarbures.marques;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
public class MarquesController {

    @Autowired
    private MarqueService marqueService;

    @GetMapping("/marques")
    @Operation(summary = "Liste des marques", description = "Lors de l'appel, on retourne un JSON de la liste des marques", responses = {
            @ApiResponse(responseCode = "200", description = "JSON ok")
    })
    Iterable<MarqueDB> getMarques() {
        return marqueService.getAllMarques();
    }

    @PostMapping("/marques")
    @Operation(summary = "Ajout d'une marques", description = "Lors de l'appel, on ajoute une marque en BDD", responses = {
            @ApiResponse(responseCode = "200", description = "ajout ok"),
            @ApiResponse(responseCode = "400", description = "JSON incorrecte"),
    })
    String postMarque(Marque marque) {
        marqueService.saveMarque(marque);
        return "ok";
    }

    @PutMapping("/marques/{marqueId}")
    @Operation(summary = "Mise à jour d'une marques", description = "Lors de l'appel, on modifie les informations d'une marque par rapport à son id", responses = {
            @ApiResponse(responseCode = "200", description = "mise à jour ok"),
            @ApiResponse(responseCode = "400", description = "JSON incorrecte"),
    })
    String putMarque(@PathVariable(value = "marqueId") UUID marqueId, @RequestBody Marque marque) {
        marqueService.updateSelectedMarque(marqueId, marque);
        return "ok";
    }

    @DeleteMapping("/marques/{marqueId}")
    @Operation(summary = "suppresion d'une marques", description = "Lors de l'appel, on supprime le document d'une marque par rapport à son id", responses = {
            @ApiResponse(responseCode = "200", description = "suppression ok"),
            @ApiResponse(responseCode = "400", description = "JSON incorrecte"),
    })
    String deleteMarque(@PathVariable(value = "marqueId") UUID marqueId) {
        marqueService.deleteMarque(marqueId);
        return "ok";
    }

}
