package com.tucarbure.tucarbures.security.usermanagement;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user-profil")
@Builder
public class UserProfilDB {

    @Id
    private String username;
    private String nom;
    private String prenom;

    public static UserProfilDBBuilder userProfilDBBuilder(){
        return UserProfilDB.builder();
    }

}
