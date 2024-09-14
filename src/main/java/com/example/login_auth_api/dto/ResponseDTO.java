package com.example.login_auth_api.dto;

import com.example.login_auth_api.domain.user.UserRole;

public record ResponseDTO(String name,UserRole role,String token) {

}
