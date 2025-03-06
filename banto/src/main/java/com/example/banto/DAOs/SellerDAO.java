package com.example.banto.DAOs;

import java.util.List;
import java.util.Optional;

import com.example.banto.DTOs.StoreDTO;
import com.example.banto.Entitys.*;
import com.example.banto.Repositorys.GroupBuyPayRepository;
import com.example.banto.Repositorys.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.banto.DTOs.ResponseDTO;
import com.example.banto.DTOs.SellerDTO;
import com.example.banto.Repositorys.SellerRepository;
import com.example.banto.Repositorys.UserRepository;

import jakarta.transaction.Transactional;

@Component
public class SellerDAO {
	@Autowired
	SellerRepository sellerRepository;
	@Autowired
	StoreRepository storeRepository;
	@Autowired
	GroupBuyPayRepository groupBuyPayRepository;
	@Autowired
	AuthDAO authDAO;
	
	public ResponseDTO findSeller() throws Exception{
		try {
			// 인증 유효 확인
			Users user = authDAO.auth(SecurityContextHolder.getContext().getAuthentication());
			Optional<Sellers>sellerOpt =  sellerRepository.findByUser_Id(user.getId());
			if(sellerOpt.isEmpty()) {
				throw new Exception("데이터 조회 오류");
			}
			else {
				Sellers seller = sellerOpt.get();
				return new ResponseDTO(SellerDTO.toDTO(seller), null);
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public void deleteMyself() throws Exception{
		try {
			// 인증 유효 확인
			Users user = authDAO.auth(SecurityContextHolder.getContext().getAuthentication());
			Optional<Sellers> sellerOpt = sellerRepository.findByUser_Id(user.getId());
			if(sellerOpt.isEmpty()) {
				throw new Exception("판매자가 아닙니다.");
			}
			else {
				Sellers seller = sellerOpt.get();
				List<Stores> stores = storeRepository.findAllBySellerIdToEntity(seller.getId());
				for(Stores store : stores){
					for(Items item : store.getItems()){
						List<GroupItemPays> payments = groupBuyPayRepository.findByItemId(item.getId());
						for(GroupItemPays payment : payments){
							payment.setItem(null);
							groupBuyPayRepository.save(payment);
						}
					}
				}
				sellerRepository.delete(seller);
				sellerRepository.flush();
			}
		}catch(Exception e) {
			e.printStackTrace();  // ✅ 로그 남기기
			throw new RuntimeException("삭제 중 오류 발생: " + e.getMessage());  // ✅ 예외 변환하여 트랜잭션 롤백 유도
		}
	}
	
	@Transactional
	public void deleteSeller(Integer userId) throws Exception{
		try {
			if(!authDAO.authRoot(SecurityContextHolder.getContext().getAuthentication())){
				throw new Exception("관리자 권한 오류");
			}
			Optional<Sellers>sellerOpt = sellerRepository.findByUser_Id(userId);
			if(sellerOpt.isEmpty()) {
				throw new Exception("판매자가 아닙니다.");
			}
			else {
				Sellers seller = sellerOpt.get();
				sellerRepository.delete(seller);
			}
		}catch(Exception e) {
			throw e;
		}
	}
}
