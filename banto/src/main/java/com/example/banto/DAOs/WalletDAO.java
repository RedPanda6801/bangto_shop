package com.example.banto.DAOs;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.banto.DTOs.ResponseDTO;
import com.example.banto.DTOs.WalletDTO;
import com.example.banto.Entitys.Users;
import com.example.banto.Entitys.Wallets;
import com.example.banto.Repositorys.UserRepository;
import com.example.banto.Repositorys.WalletRepository;

import jakarta.transaction.Transactional;

@Component
public class WalletDAO {
	@Autowired
	WalletRepository walletRepository;
	@Autowired
	UserRepository	userRepository;
	@Autowired
	AuthDAO authDAO;
	
	public ResponseDTO findWallet(Integer userId) throws Exception{
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
					return new ResponseDTO(WalletDTO.toDTO(wallet), null);
				}
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public void modifyWallet(Integer userId, WalletDTO dto) throws Exception{
		try {
			if(userId == -1) {				
				// 인증 유효 확인
				authDAO.auth(userId);
				if(dto.getWalletPk() == null) {
					throw new Exception("수정할 지갑 Pk를 입력해주세요.");
				}
				else if(dto.getCash() == null && dto.getCashBack() == null) {
					throw new Exception("수정할 내용을 입력해주세요.");
				}
				else {
					Optional<Wallets> walletOpt = walletRepository.findById(dto.getWalletPk());
					if(walletOpt.isEmpty()) {
						throw new Exception("수정할 지갑이 없습니다.");
					}
					Wallets wallet = walletOpt.get();
					if(dto.getCash() != null) {
						wallet.setCash(dto.getCash());
					}
					if(dto.getCashBack() != null) {
						wallet.setCashBack(dto.getCashBack());
					}
					walletRepository.save(wallet);
				}
			}
			else {				
				Optional<Users> userOpt = userRepository.findById(userId);
				if(userOpt.isEmpty()) {
					throw new Exception("권한 없음");
				}
				if(dto.getCash() == null) {
					throw new Exception("수전할 캐시가 없습니다.");
				}
				Optional<Wallets> walletOpt = walletRepository.findByUser_Id(userId);
				if(walletOpt.isEmpty()) {
					throw new Exception("충전할 지갑이 없습니다.");
				}
				Wallets wallet = walletOpt.get();
				wallet.setCash(wallet.getCash() + dto.getCash());
				walletRepository.save(wallet);
			}
		}catch(Exception e) {
			throw e;
		}
	}
}
