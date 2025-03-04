package com.example.banto.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.banto.DAOs.WalletDAO;
import com.example.banto.DTOs.ResponseDTO;
import com.example.banto.DTOs.WalletDTO;

@Service
public class WalletService {
	@Autowired
	WalletDAO walletDAO;
	
	public ResponseDTO getMyWallet(Integer userId) throws Exception{
		if(userId == null) { 
			throw new Exception("권한 없음");
		}
		else {
			try {
				return walletDAO.findWallet(userId);
			}catch(Exception e) {
				throw e;
			}
		}
	}
	
	public void modifyWallet(Integer userId, WalletDTO dto) throws Exception{
		if(userId == null) { 
			throw new Exception("권한 없음");
		}
		else {
			try {
				walletDAO.modifyWallet(userId, dto);
			}catch(Exception e) {
				throw e;
			}
		}
	}
}
