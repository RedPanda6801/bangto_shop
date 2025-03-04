package com.example.banto.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.banto.DAOs.StoreDAO;
import com.example.banto.DTOs.ResponseDTO;
import com.example.banto.DTOs.StoreDTO;

@Service
public class StoreService {
	@Autowired
	StoreDAO storeDAO;
	
	public void create(Integer userId, StoreDTO dto) throws Exception {
		if(dto.getName() == null) {
			throw new Exception("입력 오류");
		}
		else {
			try {
				storeDAO.create(userId, dto);
			}catch(Exception e) {
				throw e;
			}
		}
	}
	
	public ResponseDTO getMyStores(Integer userId) throws Exception {
		try {
			return storeDAO.getMyStores(userId);
		}catch(Exception e) {
			throw e;
		}
	}
	
	public ResponseDTO getStoreDetail(Integer userId, Integer storeId) throws Exception {
		try {
			return storeDAO.getStore(userId, storeId);
		}catch(Exception e) {
			throw e;
		}
	}
	public void modify(Integer userId, StoreDTO dto) throws Exception {
		try {
			// validation 필요시 추가

			// 수정 기능 호출
			storeDAO.modify(userId, dto);
		}catch(Exception e) {
			throw e;
		}
	}
	
	public ResponseDTO getMyStoresByRoot(Integer page) throws Exception {
		try {
			return storeDAO.getMyStoresByRoot(page);
		}catch(Exception e) {
			throw e;
		}
	}
}
