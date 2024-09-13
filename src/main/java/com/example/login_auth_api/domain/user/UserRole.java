package com.example.login_auth_api.domain.user;

public enum UserRole {
    ADMIN("admin"),
    USER("user");

    private String role;

    UserRole(String role){
        this.role = role;
    }
}
