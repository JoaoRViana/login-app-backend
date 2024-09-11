package com.example.login_auth_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.login_auth_api.domain.user.user;
import com.example.login_auth_api.dto.UserRequestDTO;
import com.example.login_auth_api.repositories.userRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor

public class UserController {

    private final userRepository repository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<String> getUser(){
        return ResponseEntity.ok("sucesso!");
    }

    @PutMapping
    public ResponseEntity<String>editUser(@RequestBody UserRequestDTO body){
        user user = this.repository.findByEmail(body.email()).orElseThrow(()->new RuntimeException("user not found"));
        if(passwordEncoder.matches(body.password(), user.getPassword())){
            user.setName(body.name());
            user.setPassword(passwordEncoder.encode(body.newPassword()));
            repository.save(user);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}