package com.tucarbure.tucarbures.security.usermanagement;

import com.tucarbure.tucarbures.security.JwtTokenProvider;
import com.tucarbure.tucarbures.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
public class UserController {

    @Autowired
    private UserProfilRepository userProfilRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/{username}")
    @Operation(summary = "Récupére l'utilisateur connecté", description = "Lors de l'appel, on retourne les informations de l'utilisateur connecté", responses = {
            @ApiResponse(responseCode = "200", description = "JSON des informations utilisateur ok"),
            @ApiResponse(responseCode = "400", description = "User name introuvable")
    })
    UserProfilDB getUser(@RequestHeader("Authorization") String Authorization, @PathVariable(value="username") String username) {

        String usernameAuth = userRepository.findByEmail(jwtTokenProvider.getUsername(Authorization.substring(7))).getUsername();

        if (usernameAuth.equals(username)){
            return userProfilRepository.findById(username).get();
        }

        return UserProfilDB.userProfilDBBuilder().build();

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