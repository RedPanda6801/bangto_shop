package com.example.banto.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.banto.DTOs.LoginDTO;
import com.example.banto.DTOs.UserDTO;
import com.example.banto.DTOs.WalletDTO;
import com.example.banto.JWTs.JwtUtil;
import com.example.banto.Services.UserService;
import com.example.banto.Services.WalletService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController {
	@Autowired
	UserService userService;

	@Autowired
	JwtUtil jwtUtil;
	
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
			LoginDTO token = userService.login(dto);
			return ResponseEntity.ok().body(token);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	// 내정보 조회
	@GetMapping("/user/get-info")
	public ResponseEntity getUser(HttpServletRequest request) {
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer userId = Integer.parseInt(token);
			UserDTO user = userService.getUser(userId);
			return ResponseEntity.ok().body(user);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	// 내정보 수정
	@PostMapping("/user/modify")
	public ResponseEntity modifyUser(HttpServletRequest request, @RequestBody UserDTO dto) {
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer userId = Integer.parseInt(token);
			userService.modifyUser(userId, dto);
			return ResponseEntity.ok().body(null);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	// 내 정보 삭제
	
	// 유저 전체 정보 조회(관리자)
	@GetMapping("/manager/user/get-list/{page}")
	public ResponseEntity getUserListManager(HttpServletRequest request, @PathVariable("page") Integer page) {
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer rootId = Integer.parseInt(token);
			List<UserDTO> user = userService.getUserListForRoot(rootId, page);
			return ResponseEntity.ok().body(user);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	// 유저 단일 조회(관리자)
	@GetMapping("/manager/user/get-info/{userId}")
	public ResponseEntity getUserManager(HttpServletRequest request, @PathVariable("userId") Integer userId) {
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer rootId = Integer.parseInt(token);
			UserDTO user = userService.getUserForRoot(rootId, userId);
			return ResponseEntity.ok().body(user);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	// 유저 단일 수정(관리자)
	@PostMapping("/manager/user/modify/{userId}")
	public ResponseEntity modifyUserManager(HttpServletRequest request, @PathVariable("userId") Integer userId, @RequestBody UserDTO dto) {
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer rootId = Integer.parseInt(token);
			userService.modifyUserForRoot(rootId, userId, dto);
			return ResponseEntity.ok().body(null);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	// 유저 단일 삭제(관리자)
}
