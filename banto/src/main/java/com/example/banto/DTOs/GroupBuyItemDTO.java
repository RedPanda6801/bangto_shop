package com.example.banto.DTOs;

import com.example.banto.Entitys.GroupBuyItems;
import com.example.banto.Entitys.GroupBuys;
import com.example.banto.Entitys.Items;
import com.example.banto.Entitys.Options;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupBuyItemDTO {

	private Integer id;

	private Integer limitPerBuyer;

	private Integer maxAmount;

	private Integer itemId;

	private Integer eventId;

	private Integer OptionId;
	
	private GroupBuys event;
	
	private Integer nowAmount;
	
	private Items item;
	
	private Options option;
	
	public static GroupBuyItemDTO toDTO(GroupBuyItems entity) {
		return GroupBuyItemDTO.builder()
				.id(entity.getId())
				.limitPerBuyer(entity.getLimitPerBuyer())
				.maxAmount(entity.getMaxAmount())
				.item(entity.getItem())
				.build();
	}
}
