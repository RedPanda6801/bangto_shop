package com.example.banto.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.banto.DAOs.ItemDAO;
import com.example.banto.DTOs.ItemDTO;

@Service
public class ItemService {
	@Autowired
	ItemDAO itemDAO;
	
	public List<ItemDTO> getItemsWithFilter(Integer userId, Integer storeId, Integer page) throws Exception {
		try {
			if(page == null || page < 1) {
				throw new Exception("페이지 입력 오류");
		}else {
				return itemDAO.getItemsWithFilter(userId, storeId, page);
			}
		}catch(Exception e) {
			throw e;
		}
	}
}
