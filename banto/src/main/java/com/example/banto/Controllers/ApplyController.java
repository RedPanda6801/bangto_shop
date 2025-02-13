package com.example.banto.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.banto.DTOs.ApplyDTO;
import com.example.banto.DTOs.ProcessDTO;
import com.example.banto.DTOs.StoreDTO;
import com.example.banto.JWTs.JwtUtil;
import com.example.banto.Services.ApplyService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ApplyController {
	@Autowired
	ApplyService applyService;  
	@Autowired
	JwtUtil jwtUtil;	
	
	// 판매자 인증 신청
	@PostMapping("/apply")
	public ResponseEntity apply(HttpServletRequest request) {
		try {
			// 토큰 인증
			String token = jwtUtil.validateToken(request);
			if(token != null) {
				applyService.applySellerAuth(Integer.parseInt(token));
				return ResponseEntity.ok().body(null);
			} else {
				return ResponseEntity.badRequest().body("토큰 인증 오류");
			}
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 판매자 인증 신청서 조회(본인)
	@GetMapping("/apply/my-info")
	public ResponseEntity getMyApply(HttpServletRequest request) {
		try {
			// 토큰 인증
			String token = jwtUtil.validateToken(request);
			if(token != null) {		
				ApplyDTO authInfo = applyService.getAuthInfo(Integer.parseInt(token));
				return ResponseEntity.ok().body(authInfo);
			} else {
				return ResponseEntity.badRequest().body("토큰 인증 오류");
			}
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 판매자 인증 신청서 처리(관리자)
	@PostMapping("/apply/modify")
	public ResponseEntity modifyApply(HttpServletRequest request, @RequestBody ProcessDTO dto) {
		try {
			// 토큰 인증
			String token = jwtUtil.validateToken(request);
			if(token != null) {
				Integer rootId = Integer.parseInt(token);
				applyService.modify(rootId, dto);
				return ResponseEntity.ok().body(null);
			} else {
				return ResponseEntity.badRequest().body("토큰 인증 오류");
			}
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 판매자 인증 신청서 목록 조회(관리자)
	@GetMapping("/apply/get-list")
	public ResponseEntity getApplyList(HttpServletRequest request) {
		try {
			// 토큰 인증
			String token = jwtUtil.validateToken(request);
			if(token != null) {		
				List<ApplyDTO> applies = applyService.getApplyList(Integer.parseInt(token));
				return ResponseEntity.ok().body(applies);
			} else {
				return ResponseEntity.badRequest().body("토큰 인증 오류");
			}
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 판매자 인증 신청서 세부 조회(관리자)
	@GetMapping("/apply/get-info/{sellerAuthId}")
	public ResponseEntity getApply(HttpServletRequest request, @PathVariable("sellerAuthId") Integer sellerAuthId) {
		try {
			// 토큰 인증
			String token = jwtUtil.validateToken(request);
			if(token != null) {		
				ApplyDTO apply = applyService.getApply(Integer.parseInt(token), sellerAuthId);
				return ResponseEntity.ok().body(apply);
			} else {
				return ResponseEntity.badRequest().body("토큰 인증 오류");
			}
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
