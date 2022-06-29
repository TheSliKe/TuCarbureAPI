package com.tucarbure.tucarbures.security;

import java.util.HashMap;
import java.util.Map;

import com.tucarbure.tucarbures.security.usermanagement.UserProfil;
import com.tucarbure.tucarbures.security.usermanagement.UserProfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.tucarbure.tucarbures.security.usermanagement.UserProfil.userProfilBuilder;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository users;

    @Autowired
    UserProfilRepository userProfilRepository;

    @Autowired
    private CustomUserDetailsService userService;

    @SuppressWarnings("rawtypes")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthBody data) {
        try {
            String email = data.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, data.getPassword()));
            User user = this.users.findByEmail(email);
            String token = jwtTokenProvider.createToken(email, user.getRoles());
            Map<Object, Object> model = new HashMap<>();
            model.put("email", email);
            model.put("usermane", user.getUsername());
            model.put("token", token);
            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email/password supplied");
        }
    }

    @SuppressWarnings("rawtypes")
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody User user) {
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            throw new BadCredentialsException("User with username: " + user.getEmail() + " already exists");
        }
        System.out.println(user);
        userProfilRepository.save(userProfilBuilder()
                .username(user.getUsername())
                .nom("")
                .prenom("")
                .build()
        );
        userService.saveUser(user);
        Map<Object, Object> model = new HashMap<>();
        model.put("message", "User registered successfully");
        return ok(model);
    }
}
