package com.example.banto.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.banto.DTOs.WalletDTO;
import com.example.banto.Services.WalletService;

@Controller
public class WalletController {
	@Autowired
	WalletService walletService;
	
	@GetMapping("/wallet/my/get-info/{userId}")
	public ResponseEntity getWallet(@PathVariable("userId") Integer userId) {
		try {
			// 토큰 인증으로 바뀔 예정
			WalletDTO wallet = walletService.getMyWallet(userId);
			return ResponseEntity.ok().body(wallet);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
