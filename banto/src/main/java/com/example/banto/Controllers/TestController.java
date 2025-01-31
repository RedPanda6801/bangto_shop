package com.example.banto.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

	@GetMapping("/")
	public ResponseEntity test() {
		return ResponseEntity.ok("hello");
	}
}
