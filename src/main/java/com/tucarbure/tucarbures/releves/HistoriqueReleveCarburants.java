package com.tucarbure.tucarbures.releves;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Document(collection = "historique_releve_carburants")
@Builder
public class HistoriqueReleveCarburants {

    @Id
    private UUID id;

    private ReleveCarburants releveCarburants;
    private UUID stationId;
    private LocalDateTime date;

    public static HistoriqueReleveCarburantsBuilder historiqueReleveCarburantsBuilder(){
        return HistoriqueReleveCarburants.builder();
    }

}
