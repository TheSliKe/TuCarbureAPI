package com.tucarbure.tucarbures.security.usermanagement;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@Document(collection = "user-profil")
@Builder
public class UserProfilDB {

    @Id
    private String username;
    private String nom;
    private String prenom;
    private List<UUID> releveIds;

    public static UserProfilDBBuilder userProfilDBBuilder(){
        return UserProfilDB.builder();
    }

}
