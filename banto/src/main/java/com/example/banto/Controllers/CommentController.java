package com.example.banto.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.example.banto.DTOs.CommentDTO;
import com.example.banto.DTOs.ResponseDTO;
import com.example.banto.JWTs.JwtUtil;
import com.example.banto.Services.CommentService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CommentController {
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	CommentService commentService;
	
	// 후기 작성
	@PostMapping(path = "/comment/write", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity writeComment(HttpServletRequest request, @RequestPart("dto") CommentDTO dto, @RequestPart(name = "files", required = false) List<MultipartFile> files) {
		try {
			// 토큰 인증
			String token = jwtUtil.validateToken(request);
			if(token != null) {
				commentService.writeComment(Integer.parseInt(token), dto, files);
				return ResponseEntity.ok().body("후기 작성 완료");
			} else {
				return ResponseEntity.badRequest().body("토큰 인증 오류");
			}
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 물품별 후기 목록 조회
	@GetMapping("/comment/item/{itemId}/{page}")
	public ResponseEntity getItemComment(HttpServletRequest request, @PathVariable("itemId") Integer itemId, @PathVariable("page") Integer page) {
		try {
			// 토큰 인증
			String token = jwtUtil.validateToken(request);
			if(token != null) {
				ResponseDTO comments = commentService.getItemComment(Integer.parseInt(token), itemId, page);
				return ResponseEntity.ok().body(comments);
			} else {
				return ResponseEntity.badRequest().body("토큰 인증 오류");
			}
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 후기 세부 조회
	@GetMapping("/comment/get/{commentId}")
	public ResponseEntity getComment(HttpServletRequest request, @PathVariable("commentId") Integer commentId) {
		try {
			// 토큰 인증
			String token = jwtUtil.validateToken(request);
			if(token != null) {
				ResponseDTO comment = commentService.getComment(Integer.parseInt(token), commentId);
				return ResponseEntity.ok().body(comment);
			} else {
				return ResponseEntity.badRequest().body("토큰 인증 오류");
			}
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	// 내가 작성한 후기 목록 조회
	@GetMapping("/comment/get-my/{page}")
	public ResponseEntity getMyComment(HttpServletRequest request, @PathVariable("page") Integer page) {
		try {
			// 토큰 인증
			String token = jwtUtil.validateToken(request);
			if(token != null) {
				ResponseDTO comments = commentService.getMyComment(Integer.parseInt(token), page);
				return ResponseEntity.ok().body(comments);
			} else {
				return ResponseEntity.badRequest().body("토큰 인증 오류");
			}
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
