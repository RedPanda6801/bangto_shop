package com.example.banto.Entitys;

import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class GroupChatRooms {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@OneToMany(mappedBy="room")
	private ArrayList<GroupChatMsgs> msgs = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name="BUYER_PK")
	private Users user;
	
	@OneToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="GROUP_BUY_ITEM_PK")
	private GroupBuyItems groupBuyItem;
}
