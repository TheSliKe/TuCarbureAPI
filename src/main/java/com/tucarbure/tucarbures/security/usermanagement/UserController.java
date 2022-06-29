package com.tucarbure.tucarbures.security.usermanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserProfilRepository userProfilRepository;

    @GetMapping("/user/{username}")
    UserProfilDB getUser(@PathVariable(value="username") String username) {
        return userProfilRepository.findById(username).get();
    }

    @PostMapping("/user/{username}")
    String postUser(@PathVariable(value="username") String username, @RequestBody UserProfil userProfil){

        userProfilRepository.save(UserProfilDB.userProfilDBBuilder()
                .username(username)
                .nom(userProfil.getNom())
                .prenom(userProfil.getPrenom())
                .build()
        );

        return "ok";
    }


}