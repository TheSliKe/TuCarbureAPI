package com.tucarbure.tucarbures.releves;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Document(collection = "historique_releve_carburants")
@Builder
public class HistoriqueReleveCarburants {

    @Id
    private UUID id;

    private UUID stationId;
    private LocalDateTime date;
    private Carburants carburants;
    private String username;

    public static HistoriqueReleveCarburantsBuilder historiqueReleveCarburantsBuilder(){
        return HistoriqueReleveCarburants.builder();
    }

}
