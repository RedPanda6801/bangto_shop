package com.example.banto.DAOs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.example.banto.Entitys.Items;
import com.example.banto.Entitys.Options;
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
			Optional<GroupBuys> eventOpt = groupBuyRepository.findById(dto.getEventPk());
			if(eventOpt.isEmpty()) {
				throw new Exception("이벤트 정보 없음");
			}else {
				Optional<Items> itemOpt = itemRepository.findById(dto.getItemPk());
				if(itemOpt.isEmpty() ||
					!Objects.equals(itemOpt.get().getStore().getSeller().getUser().getId(), userId)){
					throw new Exception("아이템 정보 오류");
				}
				else {
					Items item = itemOpt.get();
					boolean isOption = false;
					for(Options option : item.getOptions()) {
						if(Objects.equals(option.getId(), dto.getOptionPk())) {
							dto.setOption(option);
							isOption = true;
						}
					}
					if(!isOption) {
						throw new Exception("옵션 정보 오류");
					}
					else {
						if(dto.getOption().getAmount() < dto.getMaxAmount()) {
							throw new Exception("최대 수량 초과 오류");
						}
						dto.setItem(item);
						dto.setEvent(eventOpt.get());

						dto.setSellerPk(userId);
						GroupBuyItems itemObj = GroupBuyItems.toEntity(dto);
						itemObj.setNowAmount(dto.getMaxAmount());
						groupBuyItemRepository.save(itemObj);
					}
					}
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	public void modifyItem(Integer userId, GroupBuyItemDTO dto) throws Exception {
		try {
			Optional<GroupBuyItems> groupItemOpt = groupBuyItemRepository.findById(dto.getId());
			if(groupItemOpt.isEmpty() ||
				!Objects.equals(groupItemOpt.get().getItem().getStore().getSeller().getUser().getId(), userId)){
				throw new Exception("아이템 정보 오류");
			}
			else {
				GroupBuyItems groupBuyItem = groupItemOpt.get();
				// 판매량보다 낮은 값을 최대 수량으로 수정할 수 없음
				int sellerAmount = groupBuyItem.getMaxAmount() - groupBuyItem.getNowAmount();
				if(sellerAmount > dto.getMaxAmount()) {
					throw new Exception("최대 수량 오류");
				}
				int nowAmount = dto.getMaxAmount() - sellerAmount;

				// 수정 로직
				groupBuyItem.setMaxAmount((dto.getMaxAmount() != null && !dto.getMaxAmount().equals("")) ?
						dto.getMaxAmount() : groupBuyItem.getMaxAmount());
				groupBuyItem.setLimitPerBuyer((dto.getLimitPerBuyer() != null && !dto.getLimitPerBuyer().equals("")) ?
						dto.getLimitPerBuyer() : groupBuyItem.getLimitPerBuyer());
				groupBuyItem.setNowAmount((dto.getMaxAmount() != null && !dto.getMaxAmount().equals("")) ?
						nowAmount : groupBuyItem.getNowAmount());
				groupBuyItemRepository.save(groupBuyItem);

				GroupBuyItems itemObj = GroupBuyItems.toEntity(dto);
				itemObj.setNowAmount(dto.getMaxAmount());
				groupBuyItemRepository.save(itemObj);
			}

		}catch(Exception e) {
			throw e;
		}
	}
}
