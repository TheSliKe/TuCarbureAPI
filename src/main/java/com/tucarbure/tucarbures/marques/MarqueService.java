package com.tucarbure.tucarbures.marques;

import com.tucarbure.tucarbures.StationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class MarqueService {

    @Autowired
    private MarquesRepository marquesRepository;

    @Autowired
    private StationsRepository stationsRepository;

    @Autowired
    private MarqueMapper marqueMapper;

    Iterable<MarqueDB> getAllMarques(){
        return marquesRepository.findAll();
    }

    void saveMarque(Marque marque){
        saveMarqueDB(marqueMapper.map(marque));
    }

    void updateSelectedMarque(UUID marqueId, Marque marque){

        Optional<MarqueDB> optionalMarqueDB = marquesRepository.findById(marqueId);

        saveMarqueDB(marqueMapper.map(marqueId, marque));

        stationsRepository.findAllByMarque(Marque.builder()
                .nom(optionalMarqueDB.get().getNom())
                .description(optionalMarqueDB.get().getDescription())
                .build()).forEach(stationDB -> {
            stationDB.setMarque(marque);
            stationsRepository.save(stationDB);
        });

    }

    void deleteMarque(UUID marqueId){
        marquesRepository.deleteById(marqueId);
    }

    private void saveMarqueDB(MarqueDB marqueDB){
        marquesRepository.save(marqueDB);
    }

}
