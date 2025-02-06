package com.example.banto.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.banto.DTOs.ItemDTO;
import com.example.banto.DTOs.SellerDTO;
import com.example.banto.Services.ItemService;

@Controller
public class ItemController {
	@Autowired
	ItemService itemService;
	// 매장 별 물건 조회(20개 씩)
	@GetMapping("/item/get-items/filter/{userId}/{storeId}/{page}")
	public ResponseEntity getItemsWithFilter(@PathVariable("userId") Integer userId, @PathVariable("storeId") Integer storeId, @PathVariable("page") Integer page) {
		try {
			List<ItemDTO> itemList = itemService.getItemsWithFilter(userId, storeId, page);
			return ResponseEntity.ok().body(itemList);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	// 단일 물건 세부 조회
	
	// 물건 추가 
	
	// 물건 수정
	
	// 물건 삭제
}
