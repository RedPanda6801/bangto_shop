package com.example.banto.DAOs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.banto.DTOs.GroupBuyDTO;
import com.example.banto.DTOs.GroupBuyItemDTO;
import com.example.banto.Entitys.GroupBuyItems;
import com.example.banto.Entitys.GroupBuys;
import com.example.banto.Repositorys.GroupBuyItemRepository;
import com.example.banto.Repositorys.GroupBuyRepository;

@Component
public class GroupBuyItemDAO {
	@Autowired
	GroupBuyItemRepository groupBuyItemRepository;
	@Autowired
	GroupBuyRepository groupBuyRepository;
	
	public List<GroupBuyItemDTO> getCurrentItemList(GroupBuyDTO dto) throws Exception {
		try {
			// 현재 이벤트 찾기
			Optional<GroupBuys> eventOpt = groupBuyRepository.findById(dto.getId());
			if(eventOpt.isEmpty()) {
				throw new Exception("이벤트 정보 없음");
			}else {
				GroupBuys event = eventOpt.get();
				List<GroupBuyItems> groupItemList = groupBuyItemRepository.findAllByEventId(event.getId());
				
				List<GroupBuyItemDTO> dtos = new ArrayList<>();
				for(GroupBuyItems groupItem : groupItemList) {
					dtos.add(GroupBuyItemDTO.toDTO(groupItem));
				}
				return dtos;
			}
		}catch(Exception e) {
			throw e;
		}
	}
}
