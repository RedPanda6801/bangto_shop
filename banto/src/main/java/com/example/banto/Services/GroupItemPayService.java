package com.example.banto.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.banto.DAOs.GroupItemPayDAO;
import com.example.banto.DTOs.GroupBuyDTO;
import com.example.banto.DTOs.GroupBuyItemDTO;
import com.example.banto.DTOs.GroupItemPayDTO;
import com.example.banto.DTOs.ResponseDTO;

@Service
public class GroupItemPayService {
	@Autowired
	GroupItemPayDAO groupItemPayDAO;
	
	public ResponseDTO getPayListByItem(GroupBuyItemDTO dto) throws Exception {
		try {
			return groupItemPayDAO.getPayListByItem(dto);
		}catch(Exception e) {
			throw e;
		}
	}

	public void payGroupItem(Integer userId, GroupItemPayDTO dto) throws Exception {
		try {
			// Validation
			if(dto.getAmount() == null || dto.getItemPk() == null || dto.getAddress() == null
					|| dto.getOptionPk() == null || dto.getGroupItemPk() == null){
				throw new Exception("필수 정보 오류");
			}
			groupItemPayDAO.payGroupItem(userId, dto);
		}catch(Exception e) {
			throw e;
		}
	}

	public ResponseDTO getMyGroupPayList(Integer userId, Integer year) throws Exception {
		try {
			return groupItemPayDAO.getMyGroupPayList(userId, year);
		}catch(Exception e) {
			throw e;
		}
	}

	public void deliveringCheck(Integer sellerId, GroupItemPayDTO dto) throws Exception {
		try {
			// Validation
			if(dto.getId() == null){
				throw new Exception("필수 정보 오류");
			}
			groupItemPayDAO.deliveringCheck(sellerId, dto);
		}catch(Exception e) {
			throw e;
		}
	}

	public void deliveredCheck(Integer sellerId, GroupItemPayDTO dto) throws Exception {
		try {
			// Validation
			if(dto.getId() == null){
				throw new Exception("필수 정보 오류");
			}
			groupItemPayDAO.deliveredCheck(sellerId, dto);
		}catch(Exception e) {
			throw e;
		}
	}
}
