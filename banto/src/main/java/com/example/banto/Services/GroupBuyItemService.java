package com.example.banto.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.banto.DAOs.GroupBuyItemDAO;
import com.example.banto.DTOs.GroupBuyDTO;
import com.example.banto.DTOs.GroupBuyItemDTO;

@Service
public class GroupBuyItemService {
	@Autowired
	GroupBuyItemDAO groupBuyItemDAO;
	
	
	public List<GroupBuyItemDTO> getCurrentItemList(GroupBuyDTO dto) throws Exception {
		try {
			return groupBuyItemDAO.getCurrentItemList(dto);
		}catch(Exception e) {
			throw e;
		}
	}

	public void addItem(Integer userId, GroupBuyItemDTO dto) throws Exception {
		try {
			if(dto.getItemId() == null || dto.getLimitPerBuyer() == null
			|| dto.getMaxAmount() == null || dto.getEventId() == null
			|| dto.getOptionId() == null){
				throw new Exception("필수 정보 오류");
			}
			groupBuyItemDAO.addItem(userId, dto);
		}catch(Exception e) {
			throw e;
		}
	}
	
	public void modifyItem(Integer userId, GroupBuyItemDTO dto) throws Exception {
		try {
			if(dto.getItemId() == null){
				throw new Exception("필수 정보 오류");
			}
			groupBuyItemDAO.modifyItem(userId, dto);
		}catch(Exception e) {
			throw e;
		}
	}
}
