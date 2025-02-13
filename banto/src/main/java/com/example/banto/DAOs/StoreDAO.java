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

import com.example.banto.Configs.EnvConfig;
import com.example.banto.DTOs.SellerDTO;
import com.example.banto.DTOs.StoreDTO;
import com.example.banto.Entitys.Sellers;
import com.example.banto.Entitys.Stores;
import com.example.banto.Entitys.Users;
import com.example.banto.Repositorys.SellerRepository;
import com.example.banto.Repositorys.StoreRepository;

import jakarta.transaction.Transactional;

@Component
public class StoreDAO {
	@Autowired
	StoreRepository storeRepository;
	@Autowired
	SellerRepository sellerRepository;
	@Autowired
	SellerDAO sellerDAO;
	@Autowired
	AuthDAO authDAO;
	@Autowired
	EnvConfig envConfig;
	@Transactional
	public void create(Integer userId, StoreDTO dto) throws Exception {
		try {
			// 인증 유효 확인
			authDAO.auth(userId);
			// 판매자 가져오기
			Optional<Sellers> seller = sellerRepository.findById(userId);
			if(seller.isEmpty()) {
				throw new Exception("판매자 권한 오류");
			}
			else {
				// 매장 추가
				Stores store = Stores.toEntity(dto);
				store.setSeller(seller.get());
				storeRepository.save(store);
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	public List<StoreDTO> getMyStores(Integer userId) throws Exception {
		try {
			// 인증 유효 확인
			authDAO.auth(userId);
			// 판매자 가져오기
			SellerDTO sellerDTO = sellerDAO.findSeller(userId);
			//판매자 pk로 store 전부 찾기
			List<StoreDTO> storeList = storeRepository.findAllBySellerId(sellerDTO.getId());
			return storeList;
		}catch(Exception e) {
			throw e;
		}
	}
	
	public StoreDTO getStore(Integer userId, Integer storeId) throws Exception {
		try {
			// 인증 유효 확인
			authDAO.auth(userId);
			// 판매자 가져오기
			SellerDTO sellerDTO = sellerDAO.findSeller(userId);
			//판매자 pk로 store 전부 찾기
			Optional<Stores> storeOpt = storeRepository.findById(storeId);
			if(storeOpt.isEmpty()) {
				throw new Exception("매장 조회 오류");
			}
			else {
				Stores store = storeOpt.get();
				if(sellerDTO.getId() == store.getSeller().getId()) {
					// 필요한 데이터만 조회
					store.setItems(null);
					return StoreDTO.toDTO(store);
				}
				else {
					throw new Exception("판매자 권한 오류");
				}
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public void modify(Integer userId, StoreDTO dto) throws Exception {
		try {
			// 인증 유효 확인
			authDAO.auth(userId);
			// 판매자 가져오기
			Optional<Stores> storeOpt = storeRepository.findStoreByUserId(userId, dto.getId());
			if(storeOpt.isEmpty()) {
				throw new Exception("매장 없음");
			}
			else {
				Stores store = storeOpt.get();
				store.setBusiNum(
						(dto.getBusiNum() != null && !dto.getBusiNum().equals("")) ?
								dto.getBusiNum() : store.getBusiNum());
				store.setName((dto.getName() != null && !dto.getName().equals("")) ?
						dto.getName() : store.getName());
				storeRepository.save(store);
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	public List<StoreDTO> getMyStoresByRoot(Integer page) throws Exception {
		try {
			// 10명씩 끊기
			Pageable pageable = PageRequest.of(page-1, 10, Sort.by("id").ascending());
			Page<Stores> storeListPage = storeRepository.findAll(pageable);
			List<StoreDTO>storeList = new ArrayList<>();
			for(Stores store : storeListPage) {
				storeList.add(StoreDTO.toDTO(store));
			}
			return storeList;
		}catch(Exception e) {
			throw e;
		}
	}
}
