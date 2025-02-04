package com.example.banto.DAOs;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.banto.DTOs.UserDTO;
import com.example.banto.DTOs.WalletDTO;
import com.example.banto.Entitys.Users;
import com.example.banto.Entitys.Wallets;
import com.example.banto.Repositorys.UserRepository;
import com.example.banto.Repositorys.WalletRepository;

import jakarta.transaction.Transactional;

@Component
public class UserDAO {
	@Autowired
	UserRepository userRepository;
	@Autowired
	WalletRepository walletRepository;
	
	@Transactional
	public void sign(UserDTO dto) throws Exception{
		
		Optional<Users> userOpt = userRepository.findByEmail(dto.getEmail());
		if(userOpt.isPresent()) {
			throw new Exception("존재하는 회원 이메일입니다.");
		}
		else {
			try {
				Users user = Users.toEntity(dto);
				userRepository.save(user);
				// 개인 지갑은 1:1이기 때문에 만들어주기
				WalletDTO walletDTO = new WalletDTO();
				walletDTO.setUser(user);
				walletRepository.save(Wallets.toEntity(walletDTO));
			}catch(Exception e) {
				throw e;
			}
		}
	}
	
	public UserDTO login(String email, String pw) throws Exception{
		try {
			Optional<Users> userOpt = userRepository.findByEmail(email);
			if(userOpt.isEmpty()) {
				throw new Exception("존재하지 않는 회원입니다.");
			}
			else {
				Users user = userOpt.get();
				// 비밀번호 불일치 시 예외
				if(!user.getPw().equals(pw)) {
					throw new Exception("비밀번호 불일치");
				}
				else {
					return UserDTO.toDto(user);
				}
			}
		}catch(Exception e) {
			throw e;
		}
	}
}
