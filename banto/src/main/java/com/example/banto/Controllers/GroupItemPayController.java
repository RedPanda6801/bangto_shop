package com.example.banto.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.banto.DAOs.AuthDAO;
import com.example.banto.DTOs.GroupBuyItemDTO;
import com.example.banto.DTOs.GroupItemPayDTO;
import com.example.banto.JWTs.JwtUtil;
import com.example.banto.Services.GroupItemPayService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class GroupItemPayController {
	@Autowired
	AuthDAO authDAO;
	
	@Autowired
	GroupItemPayService groupItemPayService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	// 아이템 별로 결제 내역 조회(판매자만)
	@GetMapping("/group-pay/item/get-list")
	public ResponseEntity getPayListByItem(HttpServletRequest request, @RequestBody GroupBuyItemDTO dto) {
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer userId = Integer.parseInt(token);
			
			if(authDAO.authSeller(userId) == -1){
				return ResponseEntity.badRequest().body("권한 오류");
			}
			List<GroupItemPayDTO> payList = groupItemPayService.getPayListByItem(dto);
			return ResponseEntity.ok().body(payList);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// 내 결제 내역 조회
	@GetMapping("/group-pay/my/get-list/{year}")
	public ResponseEntity getMyGroupPayList(HttpServletRequest request, @PathVariable("year") Integer year) {
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer userId = Integer.parseInt(token);
			List<GroupItemPayDTO> payList = groupItemPayService.getMyGroupPayList(userId, year);
			return ResponseEntity.ok().body(payList);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	// 결제 처리
	@PostMapping("/group-pay/pay")
	public ResponseEntity payGroupItem(HttpServletRequest request, @RequestBody GroupItemPayDTO dto) {
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer userId = Integer.parseInt(token);
			groupItemPayService.payGroupItem(userId, dto);
			return ResponseEntity.ok().body(null);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// 배송 처리
	@PostMapping("/group-pay/delivering-check")
	public ResponseEntity deliveringCheck(HttpServletRequest request, @RequestBody GroupItemPayDTO dto) {
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer sellerId = authDAO.authSeller(Integer.parseInt(token));
			// 판매자 확인
			if(sellerId == -1){
				return ResponseEntity.badRequest().body("권한 오류");
			}
			groupItemPayService.deliveringCheck(sellerId, dto);
			return ResponseEntity.ok().body(null);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// 배송 완료 처리
	@PostMapping("/group-pay/delivered-check")
	public ResponseEntity deliveredCheck(HttpServletRequest request, @RequestBody GroupItemPayDTO dto) {
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer sellerId = authDAO.authSeller(Integer.parseInt(token));
			// 판매자 확인
			if(sellerId == -1){
				return ResponseEntity.badRequest().body("권한 오류");
			}
			groupItemPayService.deliveredCheck(sellerId, dto);
			return ResponseEntity.ok().body(null);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
