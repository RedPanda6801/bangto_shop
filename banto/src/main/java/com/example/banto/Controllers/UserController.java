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
}
