package com.example.banto.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.banto.DTOs.SellerDTO;
import com.example.banto.Services.SellerService;

@Controller
public class SellerController {
	@Autowired
	SellerService sellerService;
	
	
	// 판매자 본인 조회
	@GetMapping("/seller/get-info/{userId}")
	public ResponseEntity getSeller(@PathVariable("userId") Integer userId) {
		try {
			// 토큰 인증으로 바뀔 예정
			SellerDTO seller = sellerService.getSellerInfo(userId);
			return ResponseEntity.ok().body(seller);
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
