package com.example.banto.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.banto.DTOs.SellerDTO;
import com.example.banto.DTOs.WalletDTO;
import com.example.banto.JWTs.JwtUtil;
import com.example.banto.Services.WalletService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class WalletController {
	@Autowired
	WalletService walletService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@GetMapping("/wallet/my/get-info")
	public ResponseEntity getWallet(HttpServletRequest request) {
		try {
			// 토큰 인증
			String token = jwtUtil.validateToken(request);
			if(token != null) {
				WalletDTO wallet = walletService.getMyWallet(Integer.parseInt(token));
				return ResponseEntity.ok().body(wallet);
			} else {
				return ResponseEntity.badRequest().body("토큰 인증 오류");
			}
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
