package com.exmaple.banto.JWTs;

import java.security.Key;
import java.util.Date;

import com.example.banto.Configs.EnvConfig;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

public class JwtUtil {
	EnvConfig envConfig = new EnvConfig();
	String SECRET_KEY = envConfig.get("JWT_SECRET");
	Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	
	//토큰 발급(별명 파라미터 필요, 토큰 문자열 반환)
	public String generateToken(String username) {
		return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
	}
	
	//토큰 검증(토큰 파라미터 필요, 토큰 내부의 별명 반환)
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
	
	//Http 요청에서 헤더에 있는 토큰 추출
	public String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // "Bearer " 이후의 토큰 값만 추출
        }
        return null;
    }
	
	//토큰 추출 후 검증
	public String tokenValidation(HttpServletRequest request) {
		return validateToken(extractToken(request));
	}
}
