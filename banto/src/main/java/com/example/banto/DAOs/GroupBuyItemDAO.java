package com.example.banto.DAOs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.example.banto.Entitys.Items;
import com.example.banto.Repositorys.ItemRepository;
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
	@Autowired
	private ItemRepository itemRepository;

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

	public void addItem(Integer userId, GroupBuyItemDTO dto) throws Exception {
		try {
			// 추가할 이벤트 찾기
			Optional<GroupBuys> eventOpt = groupBuyRepository.findById(dto.getEventId());
			if(eventOpt.isEmpty()) {
				throw new Exception("이벤트 정보 없음");
			}else {
				Optional<Items> itemOpt = itemRepository.findById(dto.getItemId());
				if(itemOpt.isEmpty() ||
					!Objects.equals(itemOpt.get().getStore().getSeller().getUser().getId(), userId)){
					throw new Exception("아이템 정보 오류");
				}
				else {
					dto.setItem(itemOpt.get());
					dto.setEvent(eventOpt.get());
					GroupBuyItems itemObj = GroupBuyItems.toEntity(dto);
					groupBuyItemRepository.save(itemObj);
				}
			}
		}catch(Exception e) {
			throw e;
		}
	}
}
