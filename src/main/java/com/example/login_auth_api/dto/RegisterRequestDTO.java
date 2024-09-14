package com.example.login_auth_api.dto;

import com.example.login_auth_api.domain.user.UserRole;

public record RegisterRequestDTO(String name,String email,String password,UserRole role) {

}
