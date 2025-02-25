package com.example.banto.DAOs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.banto.DTOs.GroupBuyItemDTO;
import com.example.banto.DTOs.GroupItemPayDTO;
import com.example.banto.Entitys.GroupItemPays;
import com.example.banto.Repositorys.GroupBuyPayRepository;

@Component
public class GroupItemPayDAO {
	@Autowired
	GroupBuyPayRepository groupItemPayRepository;
	
	public List<GroupItemPayDTO> getPayListByItem(Integer userId, GroupBuyItemDTO dto) throws Exception {
		try {
			// 현재 이벤트 찾기
			List<GroupItemPays> pays = groupItemPayRepository.findByItemId(dto.getItemId());
			// 비어있으면 빈값 주기
			if(pays.size() <= 0) {
				return new ArrayList<GroupItemPayDTO>();
			}else {
				List<GroupItemPayDTO> dtos = new ArrayList<>();
				for(GroupItemPays pay : pays) {
					dtos.add(GroupItemPayDTO.toDTO(pay));
				}
				return dtos;
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
}
