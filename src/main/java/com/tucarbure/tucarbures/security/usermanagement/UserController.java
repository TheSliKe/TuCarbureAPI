package com.tucarbure.tucarbures.security.usermanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserProfilRepository userProfilRepository;

    @GetMapping("/user/{username}")
    UserProfil getStation(@PathVariable(value="username") String username) {
        return userProfilRepository.findById(username).get();
    }

}