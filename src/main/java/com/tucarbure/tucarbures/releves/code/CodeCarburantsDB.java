package com.tucarbure.tucarbures.releves.code;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "code-carburant")
@Builder
public class CodeCarburantsDB {

    @Id
    private UUID id;
    private String nom;
    private String code;

    public static CodeCarburantsDB.CodeCarburantsDBBuilder codeCarburantsDBBuilder(){
        return CodeCarburantsDB.builder();
    }
}
