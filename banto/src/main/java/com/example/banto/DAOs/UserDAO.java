package com.example.banto.DAOs;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.banto.DTOs.UserDTO;
import com.example.banto.Entitys.Users;
import com.example.banto.Repositorys.UserRepository;

@Component
public class UserDAO {
	@Autowired
	UserRepository userRepository;
	
	public void sign(UserDTO dto) throws Exception{
		
		Optional<Users> userOpt = userRepository.findByEmail(dto.getEmail());
		if(userOpt.isPresent()) {
			throw new Exception("존재하는 회원 이메일입니다.");
		}
		else {
			try {
				Users user = Users.toEntity(dto);
				userRepository.save(user);
			}catch(Exception e) {
				throw e;
			}
		}

	}
}
