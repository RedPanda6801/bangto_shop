package com.example.banto.Entitys;

import java.time.LocalDateTime;
import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupBuyItems {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="LIMIT_PER_BUYER", nullable=false)
	private Integer limitPerBuyer;
	
	@Column(name="MAX_AMOUNT",  nullable=false)
	private Integer maxAmount;
	
	// 참조 객체
	@ManyToOne
	@JoinColumn(name="EVENT_PK")
	private GroupBuys event;
	
	@ManyToOne
	@JoinColumn(name="ITEM_PK")
	private Items item;
}
