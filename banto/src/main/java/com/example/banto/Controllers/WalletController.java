package com.example.banto.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.banto.DAOs.AuthDAO;
import com.example.banto.DTOs.ResponseDTO;
import com.example.banto.DTOs.SellerDTO;
import com.example.banto.DTOs.WalletDTO;
import com.example.banto.JWTs.JwtUtil;
import com.example.banto.Services.WalletService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class WalletController {
	@Autowired
	WalletService walletService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	AuthDAO authDAO;
	
	// 내 지갑 조회
	@GetMapping("/wallet/my/get-info")
	public ResponseEntity getWallet(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 토큰 인증
			String token = jwtUtil.validateToken(request);
			if(token != null) {
				ResponseDTO wallet = walletService.getMyWallet(Integer.parseInt(token));
				return ResponseEntity.ok().body(wallet);
			} else {
				return ResponseEntity.badRequest().body("토큰 인증 오류");
			}
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 사용자 지갑 조회(관리자)
	@GetMapping("/wallet/manager/get-info/{userId}")
	public ResponseEntity getWalletByManager(HttpServletRequest request, @PathVariable("userId") Integer userId) {
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer rootId = Integer.parseInt(token);
			if(!authDAO.authRoot(rootId)) {
				return ResponseEntity.badRequest().body("Forbidden Error");
			}
			ResponseDTO wallet = walletService.getMyWallet(userId);
			return ResponseEntity.ok().body(wallet);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 캐시 충전(본인)
	// cash 필요
	@PostMapping("/wallet/my/charge")
	public ResponseEntity chargeCash(HttpServletRequest request, @RequestBody WalletDTO dto) {
		try {
			// 토큰 인증
			String token = jwtUtil.validateToken(request);
			if(token != null) {
				walletService.modifyWallet(Integer.parseInt(token), dto);
				return ResponseEntity.ok().body("캐시 충전 완료");
			} else {
				return ResponseEntity.badRequest().body("토큰 인증 오류");
			}
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}	
	
	// 지갑 수정(관리자)
	// 캐시, 캐시백을 추가하는 게 아니라 수치를 수정하는 기능
	// walletPk와, cash 혹은 cashBack 필요
	@PostMapping("/wallet/manager/modify")
	public ResponseEntity modifyWallet(HttpServletRequest request, @RequestBody WalletDTO dto) {
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer rootId = Integer.parseInt(token);
			if(!authDAO.authRoot(rootId)) {
				return ResponseEntity.badRequest().body("Forbidden Error");
			}
			walletService.modifyWallet(-1, dto);
			return ResponseEntity.ok().body("지갑 조정 완료");
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}	
}
