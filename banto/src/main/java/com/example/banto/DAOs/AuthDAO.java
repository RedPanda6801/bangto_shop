package com.example.banto.DAOs;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.banto.Configs.EnvConfig;
import com.example.banto.Entitys.Users;
import com.example.banto.Repositorys.UserRepository;

@Component
public class AuthDAO {
	@Autowired
	UserRepository userRepository;
	@Autowired
	EnvConfig envConfig;
	
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
	
	public boolean authRoot(Integer rootId) throws Exception{
		try {
			String rootEmail = envConfig.get("ROOT_EMAIL");
			Optional<Users> root = userRepository.findById(rootId);
			if(root.isEmpty() || !root.get().getEmail().equals(rootEmail)) {
				return false;
			}else {
				return true;
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
}
