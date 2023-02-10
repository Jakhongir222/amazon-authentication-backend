package com.example.amazonauthenticationbackend.controller;


import com.example.amazonauthenticationbackend.model.User;
import com.example.amazonauthenticationbackend.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.amazonauthenticationbackend.model.Role;

import java.util.Collections;
import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public void registerUser(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(Collections.singletonList(Role.USER));
        userRepository.save(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User login) {
        User user = userRepository.findByEmail(login.getEmail());
        if (user == null) {
            return "Email not found";
        }

        if (!bCryptPasswordEncoder.matches(login.getPassword(), user.getPassword())) {
            return "Incorrect password";
        }

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("roles", user.getRole())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "secretkey")
                .compact();
    }
}
