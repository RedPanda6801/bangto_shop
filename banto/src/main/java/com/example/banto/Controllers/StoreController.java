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
import com.example.banto.Services.StoreService;

@Controller
public class StoreController {
	@Autowired
	StoreService storeService;
	
	// 매장 추가
	@PostMapping("/store/add/{userId}")
	public ResponseEntity addStore(@PathVariable("userId") Integer userId, @RequestBody StoreDTO dto) throws Exception {
		try {
			// 토큰 인증으로 바뀔 예정
			storeService.create(userId, dto);
			return ResponseEntity.ok().body(null);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 내 매장 전체 조회
	@GetMapping("/store/get-list/{userId}")
	public ResponseEntity getStoreList(@PathVariable("userId") Integer userId) throws Exception {
		try {
			// 토큰 인증으로 바뀔 예정
			List<StoreDTO> stores = storeService.getMyStores(userId);
			return ResponseEntity.ok().body(stores);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 내 매장 세부 조회
	@GetMapping("/store/get-detail/{storeId}/{userId}")
	public ResponseEntity getStoreDetail(@PathVariable("userId") Integer userId, @PathVariable("storeId") Integer storeId) throws Exception {
		try {
			// 토큰 인증으로 바뀔 예정
			StoreDTO store = storeService.getStoreDetail(userId, storeId);
			return ResponseEntity.ok().body(store);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	// 매장 수정
	@PostMapping("/store/modify/{userId}")
	public ResponseEntity modifyStore(@PathVariable("userId") Integer userId, @RequestBody StoreDTO dto) throws Exception {
		try {
			// 토큰 인증으로 바뀔 예정
			storeService.modify(userId, dto);
			return ResponseEntity.ok().body(null);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	// 매장 삭제
}
