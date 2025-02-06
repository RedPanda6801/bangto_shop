package com.example.banto.DAOs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.example.banto.DTOs.ItemDTO;
import com.example.banto.DTOs.UserDTO;
import com.example.banto.DTOs.WalletDTO;
import com.example.banto.Entitys.Items;
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
	@Autowired
	AuthDAO authDAO;
	
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
					return UserDTO.toDTO(user);
				}
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	public List<UserDTO> getUserListForRoot(Integer rootId, Integer page) throws Exception{
		try {
			Optional<Users> userOpt = userRepository.findById(rootId);
			if(userOpt.isEmpty()) {
				throw new Exception("존재하지 않는 회원입니다.");
			}
			else if(!userOpt.get().getName().equals("root")) {
				throw new Exception("관리자가 아닙니다.");
			}
			else {
				// 10명씩 끊기
				Pageable pageable = PageRequest.of(page-1, 10, Sort.by("id").ascending());
				Page<Users>users = userRepository.findAll(pageable);
				List<UserDTO>userList = new ArrayList<UserDTO>();
				for(Users user : users) {
					UserDTO dto = UserDTO.toDTO(user);
					userList.add(dto);
				}
				
				return userList;
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	public UserDTO getUser(Integer userId) throws Exception{
		try {
			return UserDTO.toDTO(authDAO.auth(userId));
		}catch(Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public void modifyUser(Integer userId, UserDTO dto) throws Exception{
		try {
			Users user = authDAO.auth(userId);
			// 수정 로직
			user.setEmail((dto.getEmail() != null && !dto.getEmail().equals("")) ?
					dto.getEmail() : user.getEmail());
			user.setName((dto.getName() != null && !dto.getName().equals("")) ?
					dto.getName() : user.getName());
			user.setPw((dto.getPw() != null && !dto.getPw().equals("")) ?
					dto.getPw() : user.getPw());
			user.setPhone((dto.getPhone() != null && !dto.getPhone().equals("")) ?
					dto.getPhone() : user.getPhone());
			user.setAddr((dto.getAddr() != null && !dto.getAddr().equals("")) ?
					dto.getAddr() : user.getAddr());
			userRepository.save(user);
		}catch(Exception e) {
			throw e;
		}
	}

	public UserDTO getUserForRoot(Integer rootId, Integer userId) throws Exception{
		try {
			Optional<Users> userOpt = userRepository.findById(rootId);
			if(userOpt.isEmpty()) {
				throw new Exception("존재하지 않는 회원입니다.");
			}
			else if(!userOpt.get().getName().equals("root")) {
				throw new Exception("관리자가 아닙니다.");
			}
			else {
				Optional<Users>user = userRepository.findById(userId);
				if(user.isEmpty()) {
					throw new Exception("조회할 유저가 없음");
				}
				
				return UserDTO.toDTO(user.get());
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public void modifyUserForRoot(Integer rootId, Integer userId, UserDTO dto) throws Exception{
		try {
			Optional<Users> rootOpt = userRepository.findById(rootId);
			if(rootOpt.isEmpty()) {
				throw new Exception("존재하지 않는 회원입니다.");
			}
			else if(!rootOpt.get().getName().equals("root")) {
				throw new Exception("관리자가 아닙니다.");
			}
			else {
				Optional<Users>userOpt = userRepository.findById(userId);
				if(userOpt.isEmpty()) {
					throw new Exception("조회할 유저가 없음");
				}
				Users user = userOpt.get();
				// 수정 로직
				user.setEmail((dto.getEmail() != null && !dto.getEmail().equals("")) ?
						dto.getEmail() : user.getEmail());
				user.setName((dto.getName() != null && !dto.getName().equals("")) ?
						dto.getName() : user.getName());
				user.setPw((dto.getPw() != null && !dto.getPw().equals("")) ?
						dto.getPw() : user.getPw());
				user.setPhone((dto.getPhone() != null && !dto.getPhone().equals("")) ?
						dto.getPhone() : user.getPhone());
				user.setAddr((dto.getAddr() != null && !dto.getAddr().equals("")) ?
						dto.getAddr() : user.getAddr());
				userRepository.save(user);
			}
		}catch(Exception e) {
			throw e;
		}
	}
}
