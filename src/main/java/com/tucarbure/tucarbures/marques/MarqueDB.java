package com.tucarbure.tucarbures.marques;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Document(collection = "marques")
@Builder
public class MarqueDB {

    @Id
    private UUID id;

    private String nom;
    private String description;
    private LocalDateTime updateDate;

    public static MarqueDBBuilder marqueDB(){
        return MarqueDB.builder();
    }

}
