package com.example.banto.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.banto.DTOs.CartDTO;
import com.example.banto.DTOs.ResponseDTO;
import com.example.banto.JWTs.JwtUtil;
import com.example.banto.Services.CartService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CartController {
	@Autowired
	CartService cartService;
	@Autowired
	JwtUtil jwtUtil;
	
	// 장바구니 추가
	@PostMapping("/cart/add")
	public ResponseEntity addCart(HttpServletRequest request, @RequestBody CartDTO dto) {
		try {
			// 토큰 인증
			String token = jwtUtil.validateToken(request);
			if(token != null) {
				cartService.addCart(Integer.parseInt(token), dto);
				return ResponseEntity.ok().body("장바구니 추가 완료");
			} else {
				return ResponseEntity.badRequest().body("토큰 인증 오류");
			}
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 장바구니 조회
	@GetMapping("/cart/get-info")
	public ResponseEntity readCart(HttpServletRequest request) {
		try {
			// 토큰 인증
			String token = jwtUtil.validateToken(request);
			if(token != null) {
				ResponseDTO cartList = cartService.readCart(Integer.parseInt(token));
				return ResponseEntity.ok().body(cartList);
			} else {
				return ResponseEntity.badRequest().body("토큰 인증 오류");
			}
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 장바구니 수정
	@PostMapping("/cart/modify")
	public ResponseEntity modifyCart(HttpServletRequest request, @RequestBody CartDTO dto) {
		try {
			// 토큰 인증
			String token = jwtUtil.validateToken(request);
			if(token != null) {
				cartService.modifyCart(Integer.parseInt(token), dto);
				return ResponseEntity.ok().body("장바구니 수정 완료");
			} else {
				return ResponseEntity.badRequest().body("토큰 인증 오류");
			}
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 장바구니 삭제
	@PostMapping("/cart/delete")
	public ResponseEntity deleteCart(HttpServletRequest request, @RequestBody CartDTO dto) {
		try {
			// 토큰 인증
			String token = jwtUtil.validateToken(request);
			if(token != null) {
				cartService.deleteCart(Integer.parseInt(token), dto);
				return ResponseEntity.ok().body("장바구니 삭제 완료");
			} else {
				return ResponseEntity.badRequest().body("토큰 인증 오류");
			}
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
