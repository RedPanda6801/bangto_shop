package com.example.banto.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.banto.DAOs.GroupBuyDAO;
import com.example.banto.DTOs.GroupBuyDTO;

@Service
public class GroupBuyService {

	@Autowired
	GroupBuyDAO groupBuyDAO;
	
	public void addEvent(GroupBuyDTO dto) throws Exception {
		try {
			if(dto.getStartDate() == null || dto.getEndDate() == null) {
				throw new Exception("필수 정보 오류");
			}
			groupBuyDAO.addEvent(dto);
		}catch(Exception e) {
			throw e;
		}
	}
	
	public List<GroupBuyDTO> getEventList(Integer userId) throws Exception {
		try {
			return groupBuyDAO.getEventList(userId);
		}catch(Exception e) {
			throw e;
		}
	}
	
	public GroupBuyDTO getCurrentEvent() throws Exception {
		try {
			return groupBuyDAO.getCurrentEvent();
		}catch(Exception e) {
			throw e;
		}
	}
}
