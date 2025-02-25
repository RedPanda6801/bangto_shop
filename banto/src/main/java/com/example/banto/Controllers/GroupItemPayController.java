package com.example.banto.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
			
			if(!authDAO.authSeller(userId)){
				return ResponseEntity.badRequest().body("권한 오류");
			}
			List<GroupItemPayDTO> event = groupItemPayService.getPayListByItem(userId, dto);
			return ResponseEntity.ok().body(event);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
