package com.example.banto.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.banto.DTOs.UserDTO;
import com.example.banto.Services.UserService;

@Controller
public class UserController {
	@Autowired
	UserService userService;
	
	@PostMapping("/sign")
	public ResponseEntity sign(@RequestBody UserDTO dto) {
		try {
			userService.sign(dto);
			return ResponseEntity.ok().body(null);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody UserDTO dto) {
		try {
			// 단순히 유저 찾기에 불과, JWT 토큰 또는 세션이나 비밀번호 해시화에 대해 넣을 필요가 있음
			userService.login(dto);
			return ResponseEntity.ok().body(null);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
