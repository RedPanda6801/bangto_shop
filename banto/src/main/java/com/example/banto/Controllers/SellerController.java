package com.example.banto.Controllers;

import java.net.http.HttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.banto.DAOs.AuthDAO;
import com.example.banto.DTOs.SellerDTO;
import com.example.banto.JWTs.JwtUtil;
import com.example.banto.Services.SellerService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SellerController {
	@Autowired
	SellerService sellerService;
	
	@Autowired
	AuthDAO authDAO;
	
	@Autowired
	JwtUtil jwtUtil;
	
	// 판매자 본인 조회
	@GetMapping("/seller/get-info")
	public ResponseEntity getSeller(HttpServletRequest request) {
		try {
			// 토큰 인증
			String token = jwtUtil.validateToken(request);
			if(token != null) {		
				SellerDTO seller = sellerService.getSellerInfo(Integer.parseInt(token));
				return ResponseEntity.ok().body(seller);
			} else {
				return ResponseEntity.badRequest().body("토큰 인증 오류");
			}
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 판매자 권한 반납
	@PostMapping("/seller/delete-me")
	public ResponseEntity deleteMyself(HttpServletRequest request) {
		try {
			// 토큰 인증
			String token = jwtUtil.validateToken(request);
			if(token != null) {		
				sellerService.deleteMyself(Integer.parseInt(token));
				return ResponseEntity.ok().body("판매자 권한 반납 완료");
			} else {
				return ResponseEntity.badRequest().body("토큰 인증 오류");
			}
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 판매자 전체 정보 조회(관리자)
	
	// 판매자 단일 조회(관리자)
	
	// 판매자 단일 수정(관리자)
	
	// 판매자 권한 박탈(관리자)
	@PostMapping("/seller/delete/{userId}")
	public ResponseEntity deleteSeller(HttpServletRequest request, @PathVariable("userId") Integer userId) {
		try {
			// 토큰 인증
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer rootId = Integer.parseInt(token);
			if(!authDAO.authRoot(rootId)) {
				return ResponseEntity.badRequest().body("Forbidden Error");
			}
			sellerService.deleteSeller(userId);
			return ResponseEntity.ok().body("판매자 권한 박탈 완료");
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
