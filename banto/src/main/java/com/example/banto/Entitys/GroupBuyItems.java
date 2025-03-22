package com.example.banto.Entitys;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.example.banto.DTOs.GroupBuyItemDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SequenceGenerator(
		name = "group_item_seq",
		sequenceName = "group_item_seq",
		allocationSize = 1
)
public class GroupBuyItems {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_item_seq")
	private Integer id;
	
	@Column(name="LIMIT_PER_BUYER", nullable=false)
	private Integer limitPerBuyer;
	
	@Column(name="MAX_AMOUNT",  nullable=false)
	private Integer maxAmount;
	
	@Column(name="NOW_AMOUNT",  nullable=false)
	private Integer nowAmount;

	@Column(name="SELLER_PK")
	private Integer sellerPk;

	// 참조 객체
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="EVENT_PK")
	private GroupBuys event;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="ITEM_PK")
	private Items item;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="OPTION_PK")
	private Options option;

	public static GroupBuyItems toEntity(GroupBuyItemDTO dto){
		return GroupBuyItems.builder()
				.event(dto.getEvent())
				.item(dto.getItem())
				.option(dto.getOption())
				.limitPerBuyer(dto.getLimitPerBuyer())
				.maxAmount(dto.getMaxAmount())
				.sellerPk(dto.getSellerPk())
				.build();
	}
}
