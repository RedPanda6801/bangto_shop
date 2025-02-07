package com.example.banto.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.banto.DTOs.StoreDTO;
import com.example.banto.DTOs.WalletDTO;
import com.example.banto.JWTs.JwtUtil;
import com.example.banto.Services.StoreService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class StoreController {
	@Autowired
	StoreService storeService;
	@Autowired
	JwtUtil jwtUtil;
	
	// 매장 추가
	@PostMapping("/store/add")
	public ResponseEntity addStore(HttpServletRequest request, @RequestBody StoreDTO dto) throws Exception {
		try {
			// 토큰 인증
			String token = jwtUtil.validateToken(request);
			if(token != null) {
				storeService.create(Integer.parseInt(token), dto);
				return ResponseEntity.ok().body(null);
			}else {
				return ResponseEntity.badRequest().body("토큰 인증 오류");
			}
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 내 매장 전체 조회
	@GetMapping("/store/get-list")
	public ResponseEntity getStoreList(HttpServletRequest request) throws Exception {
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer userId = Integer.parseInt(token);
			List<StoreDTO> stores = storeService.getMyStores(userId);
			return ResponseEntity.ok().body(stores);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 내 매장 세부 조회
	@GetMapping("/store/get-detail/{storeId}")
	public ResponseEntity getStoreDetail(HttpServletRequest request, @PathVariable("storeId") Integer storeId) throws Exception {
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer userId = Integer.parseInt(token);
			StoreDTO store = storeService.getStoreDetail(userId, storeId);
			return ResponseEntity.ok().body(store);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	// 매장 수정
	@PostMapping("/store/modify")
	public ResponseEntity modifyStore(HttpServletRequest request, @RequestBody StoreDTO dto) throws Exception {
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer userId = Integer.parseInt(token);
			storeService.modify(userId, dto);
			return ResponseEntity.ok().body(null);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	// 매장 삭제
}
