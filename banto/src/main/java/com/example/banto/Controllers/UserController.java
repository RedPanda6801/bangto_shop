package com.example.banto.Controllers;

import java.net.http.HttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.banto.DTOs.UserDTO;
import com.example.banto.Services.UserService;
import com.exmaple.banto.JWTs.JwtUtil;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	JwtUtil jwtUtil = new JwtUtil();
	
	// 회원가입 기능
	@PostMapping("/sign")
	public ResponseEntity sign(@RequestBody UserDTO dto) {
		try {
			userService.sign(dto);
			return ResponseEntity.ok().body(null);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 로그인 기능
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody UserDTO dto) {
		try {
			// 단순히 유저 찾기에 불과, JWT 토큰 또는 세션이나 비밀번호 해시화에 대해 넣을 필요가 있음
			userService.login(dto);
			//email이 담긴 JWT 반환
			return ResponseEntity.ok().body(jwtUtil.generateToken(dto.getEmail()));
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 내 정보 조회
	
	// 내 정보 수정
	
	// 내 정보 삭제
	
	// 유저 전체 정보 조회(관리자)
	
	// 유저 단일 조회(관리자)
	
	// 유저 단일 수정(관리자)
	
	// 유저 단일 삭제(관리자)
}
