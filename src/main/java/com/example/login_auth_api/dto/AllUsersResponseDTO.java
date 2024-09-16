package com.example.login_auth_api.dto;
import java.util.List;
import com.example.login_auth_api.domain.user.User;

public record AllUsersResponseDTO(List<User> allUsers) {

}
