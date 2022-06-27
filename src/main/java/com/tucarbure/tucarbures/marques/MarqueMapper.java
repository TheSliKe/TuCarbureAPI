package com.tucarbure.tucarbures.marques;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.tucarbure.tucarbures.marques.MarqueDB.marqueDB;

@Component
public class MarqueMapper {

    public MarqueDB map(Marque marque){
        return marqueDB()
                .id(UUID.randomUUID())
                .nom(marque.getNom())
                .description(marque.getDescription())
                .build();
    }


    MarqueDB map(UUID uuid, Marque marque){
        return marqueDB()
                .id(uuid)
                .nom(marque.getNom())
                .description(marque.getDescription())
                .updateDate(LocalDateTime.now())
                .build();
    }

}
