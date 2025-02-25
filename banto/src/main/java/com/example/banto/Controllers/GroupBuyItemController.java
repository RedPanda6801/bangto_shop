package com.example.banto.Controllers;

import java.util.List;

import com.example.banto.DAOs.AuthDAO;
import com.example.banto.JWTs.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.banto.DTOs.GroupBuyDTO;
import com.example.banto.DTOs.GroupBuyItemDTO;
import com.example.banto.Services.GroupBuyItemService;

@Controller
public class GroupBuyItemController {
	@Autowired
	GroupBuyItemService groupBuyItemService;
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	AuthDAO authDAO;

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

	// 공동 구매 이벤트에 물건 추가(판매자만)
	@PostMapping("/group-buy/item/add")
	public ResponseEntity addGroupItem(HttpServletRequest request, @RequestBody GroupBuyItemDTO groupBuyItemDTO) throws Exception{
		try{
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer userId = Integer.parseInt(token);
			if(!authDAO.authSeller(userId)){
				return ResponseEntity.badRequest().body("권한 오류");
			}
			groupBuyItemService.addItem(userId, groupBuyItemDTO);
			return ResponseEntity.ok().body(null);
		}catch(Exception e){
			return  ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 공동 구매 이벤트에 올린 물건 수정(판매자만)
		@PostMapping("/group-buy/item/modify")
		public ResponseEntity modifyGroupItem(HttpServletRequest request, @RequestBody GroupBuyItemDTO groupBuyItemDTO) throws Exception{
			try{
				String token = jwtUtil.validateToken(request);
				if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
				Integer userId = Integer.parseInt(token);
				if(!authDAO.authSeller(userId)){
					return ResponseEntity.badRequest().body("권한 오류");
				}
				groupBuyItemService.modifyItem(userId, groupBuyItemDTO);
				return ResponseEntity.ok().body(null);
			}catch(Exception e){
				return  ResponseEntity.badRequest().body(e.getMessage());
			}
		}
}
