package com.example.banto.DAOs;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.banto.Entitys.Users;
import com.example.banto.Repositorys.UserRepository;

@Component
public class AuthDAO {
	@Autowired
	UserRepository userRepository;
	
	public Users auth(Integer userId) throws Exception{
		try {
			Optional<Users> user = userRepository.findById(userId);
			if(user.isEmpty()) {
				throw new Exception("권한 없음");
			}
			else {
				return user.get();
			}
		}catch(Exception e) {
			throw e;
		}
	}
}
