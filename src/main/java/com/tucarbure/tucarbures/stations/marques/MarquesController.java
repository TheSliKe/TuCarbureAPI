package com.tucarbure.tucarbures.stations.marques;

import com.tucarbure.tucarbures.stations.StationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
public class MarquesController {

    @Autowired
    private MarqueService marqueService;

    @GetMapping("/marques")
    Iterable<MarqueDB> getMarques() {
        return marqueService.getAllMarques();
    }

    @PostMapping("/marques")
    String postMarque(Marque marque) {
        marqueService.saveMarque(marque);
        return "ok";
    }

    @PutMapping("/marques/{marqueId}")
    String putMarque(@PathVariable(value="marqueId") UUID marqueId, @RequestBody Marque marque){
        marqueService.updateSelectedMarque(marqueId, marque);
        return "ok";
    }

    @DeleteMapping("/marques/{marqueId}")
    String deleteMarque(@PathVariable(value="marqueId") UUID marqueId){
        marqueService.deleteMarque(marqueId);
        return "ok";
    }

}
