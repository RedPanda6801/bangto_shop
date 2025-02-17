package com.example.banto.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.banto.DAOs.FavoriteDAO;
import com.example.banto.DTOs.FavoriteDTO;
import com.example.banto.DTOs.ItemDTO;

@Service
public class FavoriteService {
	@Autowired
	FavoriteDAO favoriteDAO;
	
	public void addFavorite(Integer userId, ItemDTO itemDTO) throws Exception {
		try {
			if(itemDTO.getId() == null) {
				throw new Exception("필수 정보 오류");
			}
			favoriteDAO.addFavorite(userId, itemDTO);
		}catch(Exception e) {
			throw e;
		}
	}
	
	public void deleteFavotie(Integer userId, ItemDTO itemDTO) throws Exception {
		try {
			if(itemDTO.getId() == null) {
				throw new Exception("필수 정보 오류");
			}
			favoriteDAO.deleteFavotie(userId, itemDTO);
		}catch(Exception e) {
			throw e;
		}
	}
	
	public List<FavoriteDTO> getAllFavorites(Integer userId, Integer page) throws Exception {
		try {
			return favoriteDAO.getAllFavorites(userId, page);
		}catch(Exception e) {
			throw e;
		}
	}
}
