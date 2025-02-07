package com.example.banto.Controllers;

import java.net.http.HttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.banto.DTOs.SellerDTO;
import com.example.banto.JWTs.JwtUtil;
import com.example.banto.Services.SellerService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SellerController {
	@Autowired
	SellerService sellerService;
	
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
	// 판매자 삭제
	
	// 판매자 전체 정보 조회(관리자)
	
	// 판매자 단일 조회(관리자)
	
	// 판매자 단일 수정(관리자)
	
	// 판매자 단일 삭제(관리자)
}
