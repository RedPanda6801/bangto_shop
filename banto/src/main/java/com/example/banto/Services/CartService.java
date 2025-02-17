package com.example.banto.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.banto.DAOs.CartDAO;
import com.example.banto.DTOs.CartDTO;

@Service
public class CartService {
	@Autowired
	CartDAO cartDAO;

	public void addCart(Integer userId, CartDTO dto) throws Exception{
		if(userId == null) { 
			throw new Exception("권한 없음");
		}
		else {
			try {
				cartDAO.addCart(userId, dto);
			}catch(Exception e) {
				throw e;
			}
		}
	}
	
	public List<CartDTO> readCart(Integer userId) throws Exception{
		if(userId == null) { 
			throw new Exception("권한 없음");
		}
		else {
			try {
				return cartDAO.readCart(userId);
			}catch(Exception e) {
				throw e;
			}
		}
	}
	
	public void modifyCart(Integer userId, CartDTO dto) throws Exception{
		if(userId == null) { 
			throw new Exception("권한 없음");
		}
		else {
			try {
				cartDAO.modifyCart(userId, dto);
			}catch(Exception e) {
				throw e;
			}
		}
	}
	
	public void deleteCart(Integer userId, CartDTO dto) throws Exception{
		if(userId == null) { 
			throw new Exception("권한 없음");
		}
		else {
			try {
				cartDAO.deleteCart(userId, dto);
			}catch(Exception e) {
				throw e;
			}
		}
	}
}
