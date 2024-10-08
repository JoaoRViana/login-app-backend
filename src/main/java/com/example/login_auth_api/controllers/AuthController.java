package com.example.login_auth_api.controllers;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.login_auth_api.controllers.services.UserService;
import com.example.login_auth_api.domain.user.User;
import com.example.login_auth_api.dto.LoginRequestDTO;
import com.example.login_auth_api.dto.RegisterRequestDTO;
import com.example.login_auth_api.dto.ResponseDTO;
import com.example.login_auth_api.infra.security.TokenService;
import com.example.login_auth_api.repositories.UserRepository;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
        User user = this.repository.findByEmail(body.email()).orElseThrow(()->new RuntimeException("user not found"));
        if(passwordEncoder.matches(body.password(),user.getPassword())){
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getName(),user.getRole(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
        Optional<User> user = this.repository.findByEmail(body.email());
        if(user.isEmpty()){
            User newUser = userService.createUser(body.name(), body.email(), passwordEncoder.encode(body.password()));
            this.repository.save(newUser);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
