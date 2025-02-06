package com.example.banto.Controllers;

import java.security.Key;
import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.banto.Configs.EnvConfig;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Controller
public class TestController {
	EnvConfig envConfig = new EnvConfig();
	String SECRET_KEY = envConfig.get("JWT_SECRET");
	Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

	@GetMapping("/")
	public ResponseEntity test() {
		String jwt = Jwts.builder()
                .setSubject("chlqudtjd")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
		System.out.println(jwt);
		System.out.println(validateToken(jwt));
		return ResponseEntity.ok("hello");
	}
	
	public String validateToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException e) {
            return null; // 유효하지 않은 토큰
        }
    }
}
