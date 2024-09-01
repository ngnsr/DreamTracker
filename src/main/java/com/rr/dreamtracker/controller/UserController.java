package com.rr.dreamtracker.controller;

import com.rr.dreamtracker.entity.AuthRequest;
import com.rr.dreamtracker.entity.User;
import com.rr.dreamtracker.entity.UserRole;
import com.rr.dreamtracker.service.JWTService;
import com.rr.dreamtracker.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {
    private final UserService service;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    UserController(UserService service, JWTService jwtService, AuthenticationManager authenticationManager) {
        this.service = service;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<String> addNewUser(@RequestBody User user) {
        boolean created = service.registerUser(user);

        return (created) ? ResponseEntity.status(HttpStatus.CREATED).body("User created successfully") :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not created");
    }

    @PostMapping("/generateToken")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            String token = jwtService.createToken(authRequest.getEmail(), UserRole.ROLE_USER.name());
            return ResponseEntity.ok(token);
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

    @PostMapping("/verifyToken")
    public ResponseEntity<String> verify(@RequestBody String token) {
        return ResponseEntity.ok(String.valueOf(jwtService.validateToken(token)));
    }


}

