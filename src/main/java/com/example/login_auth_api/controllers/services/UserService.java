package com.example.login_auth_api.controllers.services;

import org.springframework.stereotype.Service;

import com.example.login_auth_api.domain.user.User;
import com.example.login_auth_api.domain.user.UserRole;


@Service
public class UserService {

    public User createUser(String userName,String userEmail,String userPassword){
        User newUser = new User();
                    newUser.setEmail(userEmail);
                    newUser.setName(userPassword);
                    newUser.setPassword(userPassword);
                    String admins="@admin.com";
                    if(userEmail.contains(admins)){
                        newUser.setRole(UserRole.ADMIN);
                    }else{
                        newUser.setRole(UserRole.USER);
                    }
        return newUser;
    }

}
