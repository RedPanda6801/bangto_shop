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
import com.example.banto.DTOs.ItemDTO;
import com.example.banto.Entitys.Options;
import com.example.banto.JWTs.JwtUtil;
import com.example.banto.Services.ItemService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ItemController {
	@Autowired
	ItemService itemService;
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	AuthDAO authDAO;
	
	// 매장 별 물건 조회(20개 씩)
	@GetMapping("/item/get-itemlist/{storeId}/{page}")
	public ResponseEntity getItemList(HttpServletRequest request, @PathVariable("storeId") Integer storeId, @PathVariable("page") Integer page) {
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer userId = Integer.parseInt(token);
			List<ItemDTO> itemList = itemService.getItemList(userId, storeId, page);
			return ResponseEntity.ok().body(itemList);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	// 단일 물건 세부 조회
	@GetMapping("/item/get-detail/{itemId}")
	public ResponseEntity getItemDetail(HttpServletRequest request, @PathVariable("itemId") Integer itemId) {
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer userId = Integer.parseInt(token);
			ItemDTO item = itemService.getItemDetail(userId, itemId);
			return ResponseEntity.ok().body(item);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	// 물건 추가 
	@PostMapping("/item/add-item")
	public ResponseEntity addItem(HttpServletRequest request, @RequestBody ItemDTO itemDTO) {
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer userId = Integer.parseInt(token);
			itemService.addItem(userId, itemDTO);
			return ResponseEntity.ok().body(null);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	// 물건 수정
	
	// 옵션 수정
	
	// 물건 삭제
	
	// 관리자 매장 별 물건 조회(20개 씩)
		@GetMapping("/manager/item/get-itemlist/{storeId}/{page}")
		public ResponseEntity getItemListByRoot(HttpServletRequest request, @PathVariable("storeId") Integer storeId, @PathVariable("page") Integer page) {
			try {
				String token = jwtUtil.validateToken(request);
				if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
				Integer rootId = Integer.parseInt(token);
				if(!authDAO.authRoot(rootId)) {
					return ResponseEntity.badRequest().body("Forbidden Error");
				}
				List<ItemDTO> itemList = itemService.getItemList(-1, storeId, page);
				return ResponseEntity.ok().body(itemList);
			}catch(Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}
		
		// 관리자 단일 물건 세부 조회
		@GetMapping("/item/get-detail/{itemId}")
		public ResponseEntity getItemDetailByRoot(HttpServletRequest request, @PathVariable("itemId") Integer itemId) {
			try {
				String token = jwtUtil.validateToken(request);
				if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
				Integer rootId = Integer.parseInt(token);
				if(!authDAO.authRoot(rootId)) {
					return ResponseEntity.badRequest().body("Forbidden Error");
				}
				ItemDTO item = itemService.getItemDetail(-1, itemId);
				return ResponseEntity.ok().body(item);
			}catch(Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}
}
