package com.example.banto.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.banto.DAOs.SellerDAO;
import com.example.banto.DTOs.SellerDTO;

@Service
public class SellerService {
	@Autowired
	SellerDAO sellerDAO;
	
	public SellerDTO getSellerInfo(Integer userId) throws Exception{
		if(userId == null) { 
			throw new Exception("권한 없음");
		}
		else {
			try {
				return sellerDAO.findSeller(userId);
			}catch(Exception e) {
				throw e;
			}
		}
	}
	
	public void deleteMyself(Integer userId) throws Exception{
		if(userId == null) {
			throw new Exception("권한 없음");
		}
		else {
			try {
				sellerDAO.deleteMyself(userId);
			}catch(Exception e) {
				throw e;
			}
		}
	}
	
	public void deleteSeller(Integer userId) throws Exception{
		try {
			sellerDAO.deleteSeller(userId);
		}catch(Exception e) {
			throw e;
		}
	}
}
