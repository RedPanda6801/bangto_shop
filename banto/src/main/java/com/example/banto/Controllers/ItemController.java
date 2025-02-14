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
import com.example.banto.DTOs.OptionDTO;
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
	
	// 물품 전체 조회
	@GetMapping("/item/get-all-list/{page}")
	public ResponseEntity getItemList(@PathVariable("page") Integer page) throws Exception{
		try {
			List<ItemDTO> itemList = itemService.getAllItemList(page);
			return ResponseEntity.ok().body(itemList);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 검색어 별 조회, 카테고리 별 조회...
	
	// 매장 별 물건 조회(20개 씩)
	@GetMapping("/item/get-itemlist/{storeId}/{page}")
	public ResponseEntity getItemList(HttpServletRequest request, @PathVariable("storeId") Integer storeId, @PathVariable("page") Integer page) throws Exception{
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
	public ResponseEntity getItemDetail(HttpServletRequest request, @PathVariable("itemId") Integer itemId) throws Exception{
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
	public ResponseEntity addItem(HttpServletRequest request, @RequestBody ItemDTO itemDTO) throws Exception {
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
		@PostMapping("/item/modify")
		public ResponseEntity modifyItem(HttpServletRequest request, @RequestBody ItemDTO itemDTO) throws Exception {
			try {
				String token = jwtUtil.validateToken(request);
				if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
				Integer userId = Integer.parseInt(token);
				itemService.modifyItem(userId, itemDTO);
				return ResponseEntity.ok().body(null);
			}catch(Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}
	
	// 옵션 수정
		@PostMapping("/item/option/modify")
		public ResponseEntity modifyItemOption(HttpServletRequest request, @RequestBody OptionDTO optionDTO) throws Exception {
			try {
				String token = jwtUtil.validateToken(request);
				if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
				Integer userId = Integer.parseInt(token);
				itemService.modifyItemOption(userId, optionDTO);
				return ResponseEntity.ok().body(null);
			}catch(Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}
	// 물건 삭제
	
	// 관리자 매장 별 물건 조회(20개 씩)
		@GetMapping("/manager/item/get-itemlist/{storeId}/{page}")
		public ResponseEntity getItemListByRoot(HttpServletRequest request, @PathVariable("storeId") Integer storeId, @PathVariable("page") Integer page) throws Exception {
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
		@GetMapping("/manager/item/get-detail/{itemId}")
		public ResponseEntity getItemDetailByRoot(HttpServletRequest request, @PathVariable("itemId") Integer itemId) throws Exception{
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
		
		// 관리자 물건 수정
		@PostMapping("/manager/item/modify")
		public ResponseEntity modifyItemByRoot(HttpServletRequest request, @RequestBody ItemDTO itemDTO) throws Exception {
			try {
				String token = jwtUtil.validateToken(request);
				if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
				Integer rootId = Integer.parseInt(token);
				if(!authDAO.authRoot(rootId)) {
					return ResponseEntity.badRequest().body("Forbidden Error");
				}
				itemService.modifyItem(-1, itemDTO);
				return ResponseEntity.ok().body(null);
			}catch(Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}
			
	// 관리자 옵션 수정
		@PostMapping("/manager/item/option/modify")
		public ResponseEntity modifyItemOptionByRoot(HttpServletRequest request, @RequestBody OptionDTO optionDTO) throws Exception {
			try {
				String token = jwtUtil.validateToken(request);
				if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
				Integer rootId = Integer.parseInt(token);
				if(!authDAO.authRoot(rootId)) {
					return ResponseEntity.badRequest().body("Forbidden Error");
				}
				itemService.modifyItemOption(-1, optionDTO);
				return ResponseEntity.ok().body(null);
			}catch(Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}
		
}
