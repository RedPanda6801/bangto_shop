package com.example.banto.DTOs;

import com.example.banto.Entitys.DeliverType;
import com.example.banto.Entitys.GroupBuyItems;
import com.example.banto.Entitys.GroupItemPays;
import com.example.banto.Entitys.Users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupItemPayDTO {

	private Integer id;
	
	private Integer amount;
	
	private Integer pay;
	
	private DeliverType deleverInfo;
	
	private Users user;
	
	private GroupBuyItems groupBuyItem;
	
	public static GroupItemPayDTO toDTO(GroupItemPays entity) {
		return GroupItemPayDTO.builder()
				.id(entity.getId())
				.amount(entity.getAmount())
				.pay(entity.getPay())
				.deleverInfo(entity.getDeleverInfo())
				.groupBuyItem(entity.getGroupBuyItem())
				.build();
	}
}
