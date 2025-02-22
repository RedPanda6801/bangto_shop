package com.example.banto.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.banto.DAOs.AuthDAO;
import com.example.banto.DTOs.GroupBuyDTO;
import com.example.banto.JWTs.JwtUtil;
import com.example.banto.Services.GroupBuyService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class GroupBuyController {
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	AuthDAO authDAO;
	@Autowired
	GroupBuyService groupBuyService;
	
	// 현재 공동 구매 기간 조회 (무권한) - 수정 필요
	@GetMapping("/group-buy/current-event")
	public ResponseEntity getCurrentEvent() throws Exception {
		try {
			GroupBuyDTO event = groupBuyService.getCurrentEvent();
			return ResponseEntity.ok().body(event);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 기간 추가 (관리자)
	@PostMapping("/manager/group-buy/add")
	public ResponseEntity addEvent(HttpServletRequest request, @RequestBody GroupBuyDTO groupBuyDTO) throws Exception {
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer rootId = Integer.parseInt(token);
			// 관리자 체크
			if(!authDAO.authRoot(rootId)) {
				return ResponseEntity.badRequest().body("Forbidden Error");
			}
			groupBuyService.addEvent(groupBuyDTO);
			return ResponseEntity.ok().body(null);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 관리자 OR 판매자만 조회 가능
	@GetMapping("/group-buy/get-list")
	public ResponseEntity getEventList(HttpServletRequest request) throws Exception {
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer userId = Integer.parseInt(token);
			List<GroupBuyDTO> evenList = groupBuyService.getEventList(userId);
			return ResponseEntity.ok().body(evenList);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// 물건 추가 가능한 날짜 조회 (판매자만)
	@GetMapping("/group-buy/get-choose-list")
	public ResponseEntity getChooseList(HttpServletRequest request) throws Exception {
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer userId = Integer.parseInt(token);
			// 판매자 체크
			if(!authDAO.authSeller(userId)) {
				return ResponseEntity.badRequest().body("Forbidden Error");
			}
			List<GroupBuyDTO> evenList = groupBuyService.getChooseList();
			return ResponseEntity.ok().body(evenList);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
