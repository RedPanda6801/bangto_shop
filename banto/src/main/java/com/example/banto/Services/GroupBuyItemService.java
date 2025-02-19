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
}
