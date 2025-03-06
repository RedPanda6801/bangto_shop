package com.example.banto.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.banto.DAOs.SellerDAO;
import com.example.banto.DTOs.ResponseDTO;
import com.example.banto.DTOs.SellerDTO;

@Service
public class SellerService {
	@Autowired
	SellerDAO sellerDAO;
	
	public ResponseDTO getSellerInfo() throws Exception{
		try {
			return sellerDAO.findSeller();
		}catch(Exception e) {
			throw e;
		}
	}
	
	public void deleteMyself() throws Exception{
		try {
			sellerDAO.deleteMyself();
		}catch(Exception e) {
			throw e;
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
