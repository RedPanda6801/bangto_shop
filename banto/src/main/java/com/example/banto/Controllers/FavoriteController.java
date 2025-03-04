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
import com.example.banto.DTOs.ResponseDTO;
import com.example.banto.JWTs.JwtUtil;
import com.example.banto.Repositorys.FavoriteRepository;
import com.example.banto.Services.FavoriteService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class FavoriteController {
	@Autowired
	FavoriteRepository favoriteRepository;
	@Autowired
	FavoriteService favoriteService;
	@Autowired
	JwtUtil jwtUtil;
	
	@GetMapping("/favorite/get-list/{page}")
	public ResponseEntity getItemList(HttpServletRequest request, @PathVariable("page") Integer page) throws Exception{
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer userId = Integer.parseInt(token);
			ResponseDTO itemList = favoriteService.getAllFavorites(userId, page);
			return ResponseEntity.ok().body(itemList);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/favorite/add")
	public ResponseEntity addFavorites(HttpServletRequest request, @RequestBody ItemDTO itemDTO) throws Exception{
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer userId = Integer.parseInt(token);
			favoriteService.addFavorite(userId, itemDTO);
			return ResponseEntity.ok().body(null);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/favorite/delete")
	public ResponseEntity deleteFavorites(HttpServletRequest request, @RequestBody ItemDTO itemDTO) throws Exception{
		try {
			String token = jwtUtil.validateToken(request);
			if(token == null) return ResponseEntity.badRequest().body("토큰 인증 오류");
			Integer userId = Integer.parseInt(token);
			favoriteService.deleteFavotie(userId, itemDTO);
			return ResponseEntity.ok().body(null);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
