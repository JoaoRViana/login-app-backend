package com.example.login_auth_api.controllers;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.login_auth_api.domain.user.user;
import com.example.login_auth_api.dto.LoginRequestDTO;
import com.example.login_auth_api.dto.RegisterRequestDTO;
import com.example.login_auth_api.dto.ResponseDTO;
import com.example.login_auth_api.infra.security.tokenService;
import com.example.login_auth_api.repositories.userRepository;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController {

    private final userRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final tokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
        user user = this.repository.findByEmail(body.email()).orElseThrow(()->new RuntimeException("user not found"));
        if(passwordEncoder.matches(user.getPassword(), body.password())){
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
        Optional<user> user = this.repository.findByEmail(body.email());
        if(user.isEmpty()){
            user newUser = new user();
            newUser.setEmail(body.email());
            newUser.setName(body.name());
            newUser.setPassword(passwordEncoder.encode(body.password()));
            this.repository.save(newUser);
            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new ResponseDTO(newUser.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }
}
