package com.example.banto.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.banto.DAOs.WalletDAO;
import com.example.banto.DTOs.WalletDTO;

@Service
public class WalletService {
	@Autowired
	WalletDAO walletDAO;
	
	public WalletDTO getMyWallet(Integer userId) throws Exception{
		try {
			return walletDAO.findWallet(userId);
		}catch(Exception e) {
			throw e;
		}
	}
}
