package com.example.banto.DAOs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.banto.Configs.EnvConfig;
import com.example.banto.DTOs.LoginDTO;
import com.example.banto.DTOs.UserDTO;
import com.example.banto.DTOs.WalletDTO;
import com.example.banto.Entitys.SellerAuths;
import com.example.banto.Entitys.Sellers;
import com.example.banto.Entitys.Users;
import com.example.banto.Entitys.Wallets;
import com.example.banto.Repositorys.ApplyRepository;
import com.example.banto.Repositorys.SellerRepository;
import com.example.banto.Repositorys.UserRepository;
import com.example.banto.Repositorys.WalletRepository;
import com.example.banto.JWTs.JwtUtil;
import jakarta.transaction.Transactional;

@Component
public class UserDAO {
	@Autowired
	UserRepository userRepository;
	@Autowired
	WalletRepository walletRepository;
	@Autowired
	ApplyRepository applyRepository;
	@Autowired
	SellerRepository sellerRepository;
	@Autowired
	AuthDAO authDAO;
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	EnvConfig envConfig;
	

	public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
	
	@Transactional
	public void sign(UserDTO dto) throws Exception{
		
		Optional<Users> userOpt = userRepository.findByEmail(dto.getEmail());
		if(userOpt.isPresent()) {
			throw new Exception("존재하는 회원 이메일입니다.");
		}
		else {
			try {
				if(dto.getSnsAuth() == false) {					
					BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
					dto.setPw(passwordEncoder.encode(dto.getPw()));
				}
				else {
					dto.setEmail(dto.getEmail() + "@kakao");
				}
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
	
	public LoginDTO login(String email, String pw, Boolean snsAuth) throws Exception{
		try {
			if(snsAuth != null && snsAuth == true) {
				email += "@kakao";
			}
			Optional<Users> userOpt = userRepository.findByEmail(email);
			if(userOpt.isEmpty()) {
				throw new Exception("존재하지 않는 회원입니다.");
			}
			else {
				Users user = userOpt.get();
				if(user.getSnsAuth() == false) {
					if(pw == null) {
						throw new Exception("입력 오류");
					}
					// 비밀번호 불일치 시 예외
					BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
					if(!passwordEncoder.matches(pw, user.getPw())) {
						throw new Exception("비밀번호 불일치");
					}
				}
				else {
					
				}
				LoginDTO loginDTO = new LoginDTO(jwtUtil.generateToken(user.getId()));
				return loginDTO;
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	public List<UserDTO> getUserListForRoot(Integer page) throws Exception{
		try {
				// 10명씩 끊기
				Pageable pageable = PageRequest.of(page-1, 10, Sort.by("id").ascending());
				Page<Users>users = userRepository.findAll(pageable);
				List<UserDTO>userList = new ArrayList<UserDTO>();
				for(Users user : users) {
					UserDTO dto = UserDTO.toDTO(user);
					userList.add(dto);
				}				
				return userList;
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

	public UserDTO getUserForRoot(Integer userId) throws Exception{
		try {
			Optional<Users>user = userRepository.findById(userId);
			if(user.isEmpty()) {
				throw new Exception("조회할 유저가 없음");
			}
			return UserDTO.toDTO(user.get());
		}catch(Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public void modifyUserForRoot(Integer userId, UserDTO dto) throws Exception{
		try {
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
		}catch(Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public void deleteMyself(Integer userId) throws Exception{
		try {
			/*// 지갑이 1:1관계로 바로 생성되므로 지갑을 삭제해야 됨.
			Optional<Wallets> walletOpt = walletRepository.findByUser_Id(userId);
			walletRepository.delete(walletOpt.get());
			// 판매자 인증 신청서가 있을 경우 삭제해야 함.
			List<SellerAuths> sellerAuths = applyRepository.findAllByUserId(userId);
			for(SellerAuths auth : sellerAuths) {
				applyRepository.delete(auth);
			}
			// 판매자일 경우 권한을 반납해야 함.
			Optional<Sellers> sellerOpt = sellerRepository.findByUser_Id(userId);
			sellerRepository.delete(sellerOpt.get());
			// 추후에 다른 항목들도 삭제해야 될 수도 있음.*/
			
			Users user = authDAO.auth(userId);
			userRepository.delete(user);
			
			//Optional<Wallets> walletOpt = walletRepository.findByUser_Id(userId);
			//walletRepository.delete(walletOpt.get());
		}catch(Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public void deleteUser(Integer userId) throws Exception{
		try {
			/*
			// 지갑이 1:1관계로 바로 생성되므로 지갑을 삭제해야 됨.
			Optional<Wallets> walletOpt = walletRepository.findByUser_Id(userId);
			walletRepository.delete(walletOpt.get());
			// 판매자 인증 신청서가 있을 경우 삭제해야 함.
			List<SellerAuths> sellerAuths = applyRepository.findAllByUserId(userId);
			for(SellerAuths auth : sellerAuths) {
				applyRepository.delete(auth);
			}
			// 판매자일 경우 권한을 박탈해야 함.
			Optional<Sellers> sellerOpt = sellerRepository.findByUser_Id(userId);
			if(sellerOpt.isPresent()) {
				sellerRepository.delete(sellerOpt.get());
			}
			// 추후에 다른 항목들도 삭제해야 될 수도 있음.
			*/
			Users user = authDAO.auth(userId);
			userRepository.delete(user);
		}catch(Exception e) {
			throw e;
		}
	}
	
	public Boolean isSnsSigned(String email) throws Exception{
		try {
			Optional<Users> userOpt = userRepository.findByEmail(email + "@kakao");
			if(userOpt.isEmpty()) {
				return false;
			}
			return true;
		}catch(Exception e) {
			throw e;
		}
	}
}
