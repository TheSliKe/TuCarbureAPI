package com.tucarbure.tucarbures.security.usermanagement;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
public class UserProfil {

    @Id
    private String username;
    private String nom;
    private String prenom;

    public static UserProfilBuilder userProfilBuilder(){
        return UserProfil.builder();
    }

}
