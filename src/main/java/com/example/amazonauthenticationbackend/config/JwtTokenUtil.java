package com.example.amazonauthenticationbackend.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
    }
}
