package com.example.banto.Controllers;

import java.net.http.HttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.banto.DAOs.AuthDAO;
import com.example.banto.DTOs.ResponseDTO;
import com.example.banto.DTOs.SellerDTO;
import com.example.banto.JWTs.JwtUtil;
import com.example.banto.Services.SellerService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SellerController {
	@Autowired
	SellerService sellerService;
	
	// 판매자 본인 조회
	@GetMapping("/seller/get-info")
	public ResponseEntity getSeller() {
		try {
			ResponseDTO seller = sellerService.getSellerInfo();
			return ResponseEntity.ok().body(seller);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 판매자 권한 반납
	@PostMapping("/seller/delete-me")
	public ResponseEntity deleteMyself() {
		try {
			sellerService.deleteMyself();
			return ResponseEntity.ok().body("판매자 권한 반납 완료");
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 판매자 전체 정보 조회(관리자)
	
	// 판매자 단일 조회(관리자)
	
	// 판매자 단일 수정(관리자)
	
	// 판매자 권한 박탈(관리자)
	@PostMapping("/seller/delete/{userId}")
	public ResponseEntity deleteSeller(@PathVariable("userId") Integer userId) {
		try {
			sellerService.deleteSeller(userId);
			return ResponseEntity.ok().body("판매자 권한 박탈 완료");
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
