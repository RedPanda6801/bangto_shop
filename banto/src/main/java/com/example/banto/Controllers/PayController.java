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
import com.example.banto.DTOs.PayDTO;
import com.example.banto.DTOs.SoldItemDTO;
import com.example.banto.JWTs.JwtUtil;
import com.example.banto.Services.PayService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class PayController {
	@Autowired
	PayService payService;
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	AuthDAO authDAO;
	
	
	// 장바구니 결제(배송비까지 생각해서 금액 지불해야 함)
	@PostMapping("/pay")
	public ResponseEntity payCart(HttpServletRequest request, @RequestBody PayDTO dto) {
		try {
			// 토큰 인증
			String token = jwtUtil.validateToken(request);
			if(token != null) {
				payService.payCart(Integer.parseInt(token), dto);
				return ResponseEntity.ok().body("장바구니 결제 완료");
			} else {
				return ResponseEntity.badRequest().body("토큰 인증 오류");
			}
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 개인 결제내역 확인
	@GetMapping("/pay/get-info/{page}")
	public ResponseEntity getMyPay(HttpServletRequest request, @PathVariable("page") Integer page) {
		try {
			// 토큰 인증
			String token = jwtUtil.validateToken(request);
			if(token != null) {
				List<SoldItemDTO> soldItemList = payService.getPayList(Integer.parseInt(token), page);
				return ResponseEntity.ok().body(soldItemList);
			} else {
				return ResponseEntity.badRequest().body("토큰 인증 오류");
			}
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 구매자 결제내역 확인(관리자)
	@GetMapping("/pay/get-user-info/{userId}/{page}")
	public ResponseEntity getUserPay(HttpServletRequest request, @PathVariable("userId") Integer userId, @PathVariable("page") Integer page) {
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer rootId = Integer.parseInt(token);
			if(!authDAO.authRoot(rootId)) {
				return ResponseEntity.badRequest().body("Forbidden Error");
			}
			List<SoldItemDTO> soldItemList = payService.getPayList(-1, page);
			return ResponseEntity.ok().body(soldItemList);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 판매물품 처리(판매자)
	// 배송중, 배송완료 처리
	// id, deliverInfo 필요
	@PostMapping("/pay/modify")
	public ResponseEntity modifySoldItem(HttpServletRequest request, @RequestBody SoldItemDTO dto) {
		try {
			// 토큰 인증
			String token = jwtUtil.validateToken(request);
			if(token != null) {
				payService.modifySoldItem(Integer.parseInt(token), dto);
				return ResponseEntity.ok().body("구매/판매물품 처리 완료");
			} else {
				return ResponseEntity.badRequest().body("토큰 인증 오류");
			}
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 매장 판매내역 확인(판매자)
	@GetMapping("/pay/get-my-store-info/{storeId}/{page}")
	public ResponseEntity getMySold(HttpServletRequest request, @PathVariable("storeId") Integer storeId, @PathVariable("page") Integer page) {
		try {
			// 토큰 인증
			String token = jwtUtil.validateToken(request);
			if(token != null) {
				List<SoldItemDTO> soldItemList = payService.getSoldList(Integer.parseInt(token), storeId, page);
				return ResponseEntity.ok().body(soldItemList);
			} else {
				return ResponseEntity.badRequest().body("토큰 인증 오류");
			}
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 매장 판매내역 확인(관리자)
	@GetMapping("/pay/get-store-info/{storeId}/{page}")
	public ResponseEntity getStoreSold(HttpServletRequest request, @PathVariable("storeId") Integer storeId, @PathVariable("page") Integer page) {
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer rootId = Integer.parseInt(token);
			if(!authDAO.authRoot(rootId)) {
				return ResponseEntity.badRequest().body("Forbidden Error");
			}
			List<SoldItemDTO> soldItemList = payService.getSoldList(-1, storeId, page);
			return ResponseEntity.ok().body(soldItemList);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
