package com.example.banto.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.banto.DTOs.FavoriteDTO;
import com.example.banto.DTOs.ItemDTO;
import com.example.banto.DTOs.QNADTO;
import com.example.banto.DTOs.ResponseDTO;
import com.example.banto.JWTs.JwtUtil;
import com.example.banto.Services.QNAService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class QNAController {
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	QNAService qnaService;
	
	// 매장 별 QNA 전체 조회(판매자)
	// 답변 대기중인 QNA 우선 조회
	@GetMapping("/qna/store/get-list/{page}")
	public ResponseEntity getListByStore(HttpServletRequest request, @RequestBody QNADTO qnaDTO, @PathVariable("page") Integer page) throws Exception{
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer userId = Integer.parseInt(token);
			qnaDTO.setUserPk(userId);
			ResponseDTO itemList = qnaService.getListByStore(qnaDTO, page);
			return ResponseEntity.ok().body(itemList);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

//	내 QNA 조회(고객)
	@GetMapping("/qna/get-list/{page}")
	public ResponseEntity getItemList(HttpServletRequest request, @PathVariable("page") Integer page) throws Exception{
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer userId = Integer.parseInt(token);
			ResponseDTO favoriteList = qnaService.getMyList(userId, page);
			return ResponseEntity.ok().body(favoriteList);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// QNA 세부 조회(판매자 or 고객)
	@GetMapping("/qna/get-detail")
	public ResponseEntity getQnaDetail(HttpServletRequest request, @RequestBody QNADTO qnaDTO) throws Exception{
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer userId = Integer.parseInt(token);
			ResponseDTO itemList = qnaService.getQnaDetail(userId, qnaDTO);
			return ResponseEntity.ok().body(itemList);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// QNA 추가(고객)
	@PostMapping("/qna/add")
	public ResponseEntity getItemList(HttpServletRequest request, @RequestBody QNADTO qnaDTO) throws Exception{
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer userId = Integer.parseInt(token);
			qnaService.addQNA(userId, qnaDTO);
			return ResponseEntity.ok().body(null);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// QNA 답변 추가(판매자)
	@PostMapping("/qna/add-answer")
	public ResponseEntity addSellerAnswer(HttpServletRequest request, @RequestBody QNADTO qnaDTO) throws Exception{
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer userId = Integer.parseInt(token);
			qnaService.addSellerAnswer(userId, qnaDTO);
			return ResponseEntity.ok().body(null);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

//	// QNA 삭제
//	@GetMapping("/item/get-itemlist/{storeId}/{page}")
//	public ResponseEntity getItemList(HttpServletRequest request, @PathVariable("storeId") Integer storeId, @PathVariable("page") Integer page) throws Exception{
//		try {
//			String token = jwtUtil.validateToken(request);
//			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
//			Integer userId = Integer.parseInt(token);
//			List<ItemDTO> itemList = itemService.getItemList(userId, storeId, page);
//			return ResponseEntity.ok().body(itemList);
//		}catch(Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}
}
