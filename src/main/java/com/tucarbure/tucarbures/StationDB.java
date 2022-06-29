package com.tucarbure.tucarbures;

import com.tucarbure.tucarbures.marques.Marque;
import com.tucarbure.tucarbures.releves.Carburants;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Document(collection = "stations")
@Builder
public class StationDB {

    @Id
    private UUID id;
    private Marque marque;
    private Adresse adresse;
    private Carburants carburants;
    private LocalDateTime updatedDate;

    public static StationDB.StationDBBuilder stationDB(){
        return StationDB.builder();
    }
}
