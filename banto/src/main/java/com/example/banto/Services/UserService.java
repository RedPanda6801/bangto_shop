package com.example.banto.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.banto.DAOs.UserDAO;
import com.example.banto.DTOs.UserDTO;

@Service
public class UserService {
	@Autowired
	UserDAO userDAO;
	
	public void sign(UserDTO dto) throws Exception {
		// validation
		if(dto.getEmail() == null || dto.getName() == null || dto.getPw() == null) {
			throw new Exception("입력 오류");
		}
		// SNS 로그인이 아님
		dto.setSnsAuth(false);
		dto.setSellerState(false);
		// 비밀번호 해시화 해야함(security 사용?)
		//dto.setPw(securityConfig.passwordEncoder().encode(dto.getPw()));
		try {
			userDAO.sign(dto);
		}catch(Exception e) {
			throw e;
		}
	}
	
	public UserDTO login(UserDTO dto) throws Exception {
		if(dto.getEmail() == null || dto.getPw() == null) {
			throw new Exception("입력 오류");
		}
		try {
			return userDAO.login(dto.getEmail(), dto.getPw());
		}catch(Exception e) {
			throw e;
		}
	}
}
