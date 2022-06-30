package com.tucarbure.tucarbures.security.usermanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
public class UserController {

    @Autowired
    private UserProfilRepository userProfilRepository;

    @GetMapping("/user/{username}")
    @Operation(summary = "Récupére l'utilisateur connecté", description = "Lors de l'appel, on retourne les informations de l'utilisateur connecté", responses = {
            @ApiResponse(responseCode = "200", description = "JSON des informations utilisateur ok"),
            @ApiResponse(responseCode = "400", description = "User name introuvable")
    })
    UserProfil getStation(@PathVariable(value = "username") String username) {
        return userProfilRepository.findById(username).get();
    }

}