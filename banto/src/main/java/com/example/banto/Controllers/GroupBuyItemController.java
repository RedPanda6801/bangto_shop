package com.example.banto.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.banto.DTOs.GroupBuyDTO;
import com.example.banto.DTOs.GroupBuyItemDTO;
import com.example.banto.Services.GroupBuyItemService;

@Controller
public class GroupBuyItemController {
	@Autowired
	GroupBuyItemService groupBuyItemService;
	
	// 현재 공동 구매 물건 조회
	@GetMapping("/group-buy/item/current-list")
	public ResponseEntity getCurrentEvent(@RequestBody GroupBuyDTO groupBuyDTO) throws Exception {
		try {
			List<GroupBuyItemDTO> event = groupBuyItemService.getCurrentItemList(groupBuyDTO);
			return ResponseEntity.ok().body(event);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
