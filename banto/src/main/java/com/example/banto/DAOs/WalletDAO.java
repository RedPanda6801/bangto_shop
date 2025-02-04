package com.example.banto.DAOs;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.banto.DTOs.WalletDTO;
import com.example.banto.Entitys.Users;
import com.example.banto.Entitys.Wallets;
import com.example.banto.Repositorys.UserRepository;
import com.example.banto.Repositorys.WalletRepository;

@Component
public class WalletDAO {
	@Autowired
	WalletRepository walletRepository;
	@Autowired
	UserRepository	userRepository;
	
	public WalletDTO findWallet(Integer userId) throws Exception{
		try {
			Optional<Users> userOpt = userRepository.findById(userId);
			if(userOpt.isEmpty()) {
				throw new Exception("권한 없음");
			}
			else {
				Optional<Wallets>walletOpt =  walletRepository.findByUser_Id(userId);
				if(walletOpt.isEmpty()) {
					throw new Exception("지갑이 연결되어 있지 않음");
				}
				else {
					Wallets wallet = walletOpt.get();
					wallet.setUser(null);
					return WalletDTO.toDTO(wallet);
				}
			}
		}catch(Exception e) {
			throw e;
		}
	}
}
