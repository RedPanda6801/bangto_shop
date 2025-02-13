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
	
	public List<ItemDTO> getItemList(Integer userId, Integer storeId, Integer page) throws Exception {
		try {
			if(page == null || page < 1) {
				throw new Exception("페이지 입력 오류");
		}else {
				return itemDAO.getItemList(userId, storeId, page);
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	public ItemDTO getItemDetail(Integer userId, Integer itemId) throws Exception {
		try {
			return itemDAO.getItemDetail(userId, itemId);
		}catch(Exception e) {
			throw e;
		}
	}
	
	public void addItem(Integer userId, ItemDTO itemDTO) throws Exception {
		try {
			itemDAO.addItem(userId, itemDTO);
		}catch(Exception e) {
			throw e;
		}
	}
}
