package com.tucarbure.tucarbures;

import com.tucarbure.tucarbures.releves.ReleveCarburants;
import com.tucarbure.tucarbures.marques.Marque;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Document(collection = "stations")
@Builder
public class StationDB {

    @Id
    private UUID id;
    private Marque marque;
    private Adresse adresse;
    private List<ReleveCarburants> releveCarburants;
    private LocalDateTime updatedDate;

    public static StationDB.StationDBBuilder stationDB(){
        return StationDB.builder();
    }
}
