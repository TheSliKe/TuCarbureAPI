package com.tucarbure.tucarbures.stations.marques;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class MarquesController {

    @Autowired
    private MarquesRepository marquesRepository;

    @Autowired
    private MarqueMapper marqueMapper;

    @GetMapping("/marques")
    Iterable<MarqueDB> getMarques() {
        return marquesRepository.findAll();
    }

    @PostMapping("/marques")
    String postMarque(Marque marque) {
        marquesRepository.save(marqueMapper.map(marque));
        return "ok";
    }

    @PutMapping("/marques/{marqueId}")
    String putMarque(@PathVariable(value="marqueId") UUID marqueId, @RequestBody Marque marque){
        marquesRepository.save(marqueMapper.map(marqueId, marque));
        return "ok";
    }

    @DeleteMapping("/marques/{marqueId}")
    String deleteMarque(@PathVariable(value="marqueId") UUID marqueId){
        marquesRepository.deleteById(marqueId);
        return "ok";
    }

}
