package com.example.amazonauthenticationbackend.repository;

import com.example.amazonauthenticationbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
