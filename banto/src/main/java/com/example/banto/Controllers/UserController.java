package com.example.banto.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.banto.DAOs.AuthDAO;
import com.example.banto.DTOs.LoginDTO;
import com.example.banto.DTOs.ResponseDTO;
import com.example.banto.DTOs.UserDTO;
import com.example.banto.JWTs.JwtUtil;
import com.example.banto.Services.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController {
	@Autowired
	UserService userService;

	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	AuthDAO authDAO;
	
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
			ResponseDTO token = userService.login(dto);
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
			ResponseDTO user = userService.getUser(userId);
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
	
	// 회원탈퇴
	@PostMapping("/user/delete-me")
	public ResponseEntity deleteMyself(HttpServletRequest request) {
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer userId = Integer.parseInt(token);
			userService.deleteMyself(userId);
			return ResponseEntity.ok().body("회원탈퇴 완료");
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 유저 전체 정보 조회(관리자)
	@GetMapping("/manager/user/get-list/{page}")
	public ResponseEntity getUserListManager(HttpServletRequest request, @PathVariable("page") Integer page) {
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer rootId = Integer.parseInt(token);
			if(!authDAO.authRoot(rootId)) {
				return ResponseEntity.badRequest().body("Forbidden Error");
			}
			ResponseDTO user = userService.getUserListForRoot(page);
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
			if(!authDAO.authRoot(rootId)) {
				return ResponseEntity.badRequest().body("Forbidden Error");
			}
			ResponseDTO user = userService.getUserForRoot(userId);
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
			if(!authDAO.authRoot(rootId)) {
				return ResponseEntity.badRequest().body("Forbidden Error");
			}
			userService.modifyUserForRoot(userId, dto);
			return ResponseEntity.ok().body(null);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	// 유저 단일 삭제(관리자)
	@PostMapping("/manager/user/delete/{userId}")
	public ResponseEntity deleteUser(HttpServletRequest request, @PathVariable("userId") Integer userId) {
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer rootId = Integer.parseInt(token);
			if(!authDAO.authRoot(rootId)) {
				return ResponseEntity.badRequest().body("Forbidden Error");
			}
			userService.deleteUser(userId);
			return ResponseEntity.ok().body("회원 추방 완료");
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	// SNS 회원가입 여부 확인
	@GetMapping("/user/get-sns-signed/{email}")
	public ResponseEntity isSnsSigned(@PathVariable("email") String email) {
		try {
			ResponseDTO isSnsSigned = userService.isSnsSigned(email);
			return ResponseEntity.ok().body(isSnsSigned);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
