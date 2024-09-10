package com.example.login_auth_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

public interface userRepository extends JpaRepository<User,String>{

}
