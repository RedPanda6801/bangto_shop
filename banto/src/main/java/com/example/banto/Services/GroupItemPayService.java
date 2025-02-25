package com.example.banto.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.banto.DAOs.GroupItemPayDAO;
import com.example.banto.DTOs.GroupBuyDTO;
import com.example.banto.DTOs.GroupBuyItemDTO;
import com.example.banto.DTOs.GroupItemPayDTO;

@Service
public class GroupItemPayService {
	@Autowired
	GroupItemPayDAO groupItemPayDAO;
	
	public List<GroupItemPayDTO> getPayListByItem(Integer userId, GroupBuyItemDTO dto) throws Exception {
		try {
			return groupItemPayDAO.getPayListByItem(userId, dto);
		}catch(Exception e) {
			throw e;
		}
	}
}
