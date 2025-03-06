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
import org.springframework.http.HttpStatus;
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

import io.jsonwebtoken.JwtException;
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
	
	public enum UserRole {
		BUYER, SELLER, ADMIN;
	}
	
	// 토큰이 있어야 하는 URL인지 확인
	public boolean requiresAuthentication(HttpServletRequest request) {
		String path = request.getRequestURI();
		// 토큰이 필요없는 모든 URL들 등록
		return !(path.startsWith("/group-buy/current-event")
				|| path.startsWith("/group-buy/item/current-list")
				|| path.startsWith("/group-item/event/get-list")
				|| path.startsWith("/item/get-all-list")
				|| path.startsWith("/item/get-itemlist")
				|| path.startsWith("/item/get-detail")
				|| path.startsWith("/sign")
				|| path.startsWith("/login")
				|| path.startsWith("/user/get-sns-signed")
				//|| path.startsWith("/user/get-info")
				|| path.startsWith("/comment/item")
				|| path.startsWith("/comment/get")
				|| path.startsWith("/item/get-by-title")
				|| path.startsWith("/item/get-by-store-name")
				|| path.startsWith("/item/get-by-category")
				|| path.startsWith("/item/get-filtered-list"));
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			if(!requiresAuthentication(request)) {
				filterChain.doFilter(request, response);
				return;
			}
			
			String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
			if(authorizationHeader == null) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰이 필요합니다.");
				return;
			}
			
			String token = jwtUtil.extractToken(request);
			String userIdString = jwtUtil.parseToken(token);
			if(userIdString == null) {
				if(jwtUtil.isTokenExpired(token)) {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "만료된 토큰입니다.");
					return;
				}
				else {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰입니다.");
					return;
				}
			}
			Integer userId = Integer.parseInt(userIdString);
			Optional<Users> userOpt = userRepository.findById(userId);
			if(userOpt.isEmpty()) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "권한이 없습니다.");
				return;
			}
			Optional<Sellers> sellerOpt = sellerRepositoy.findByUser_Id(userId);
			String userRole = null;
			if(userOpt.get().getEmail().equals(envConfig.get("ROOT_EMAIL"))) {
				userRole = UserRole.ADMIN.name();
				//System.out.println("ADMIN 역할");
			}
			else if(sellerOpt.isPresent()) {
				userRole = UserRole.SELLER.name();
				//System.out.println("SELLER 역할");
			}
			else {
				userRole = UserRole.BUYER.name();
				//System.out.println("BUYER 역할");
			}
			
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, null, List.of(new SimpleGrantedAuthority(userRole)));
			
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			throw new ServletException(e);
			//throw e;
		}
        
	}
	
}



