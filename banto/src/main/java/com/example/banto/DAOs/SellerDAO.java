package com.example.banto.DAOs;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.banto.DTOs.SellerDTO;
import com.example.banto.Entitys.Users;
import com.example.banto.Entitys.Sellers;
import com.example.banto.Repositorys.SellerRepository;
import com.example.banto.Repositorys.UserRepository;

@Component
public class SellerDAO {
	@Autowired
	SellerRepository sellerRepository;
	@Autowired
	AuthDAO authDAO;
	
	public SellerDTO findSeller(Integer userId) throws Exception{
	try {
			// 인증 유효 확인
			authDAO.auth(userId);
			Optional<Sellers>sellerOpt =  sellerRepository.findByUser_Id(userId);
			if(sellerOpt.isEmpty()) {
				throw new Exception("데이터 조회 오류");
			}
			else {
				Sellers seller = sellerOpt.get();
				return SellerDTO.toDTO(seller);
			}
		}catch(Exception e) {
			throw e;
		}
	}
}
