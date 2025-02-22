package com.example.banto.Configs;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.banto.Entitys.Sellers;
import com.example.banto.Entitys.Users;
import com.example.banto.JWTs.JwtUtil;
import com.example.banto.Repositorys.SellerRepository;
import com.example.banto.Repositorys.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenFilter extends OncePerRequestFilter{
	@Autowired
	EnvConfig envConfig;
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	UserRepository userRepository;
	@Autowired
	SellerRepository sellerRepositoy;
	
	/*
	// 비밀번호 해시 기능
	@Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    */
	
	public enum UserRole {
		BUYER, SELLER, ADMIN;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
			if(authorizationHeader == null) {
				filterChain.doFilter(request, response);
				return;
			}
			Integer userId = Integer.parseInt(jwtUtil.validateToken(request));
			Optional<Users> userOpt = userRepository.findById(userId);
			Optional<Sellers> sellerOpt = sellerRepositoy.findByUser_Id(userId);
			String userRole = null;
			if(userOpt.isPresent() && userOpt.get().getEmail().equals(envConfig.get("ROOT_EMAIL"))) {
				userRole = UserRole.ADMIN.name();
				//System.out.println("ADMIN 역할");
			}
			else if(sellerOpt.isPresent()) {
				userRole = UserRole.SELLER.name();
				//System.out.println("SELLER 역할");
			}
			else if(userOpt.isPresent()) {
				userRole = UserRole.BUYER.name();
				//System.out.println("BUYER 역할");
			}
			
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, null, List.of(new SimpleGrantedAuthority(userRole)));
			
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
	}
	
}



