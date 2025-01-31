package com.example.banto.Entitys;

import java.time.LocalDateTime;
import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Users {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="EMAIL", nullable=false, unique=true)
	private String email;
	
	@Column(name="PW",  nullable=true)
	private String pw;
	
	@Column(name="NAME", nullable=false)
	private String name;
	
	@Column(name="ADDR", nullable=true)
	private String addr;
	
	@Column(name="PHONE", nullable=true)
	private String phone;
	
	@Column(name="SELLER_STATE", nullable=false)
	private Boolean sellerState = false;
	
	@Column(name = "REG_DATE", nullable=false, columnDefinition = "date default sysdate")
	private LocalDateTime regDate;
	
	@Column(name="SNS_AUTH", nullable=false)
	private Boolean snsAuth = false;
	
	// 참조 객체
	@OneToMany(mappedBy="user")
	private ArrayList<SellerAuths> sellers = new ArrayList<>();
	
	@OneToMany(mappedBy="user")
	private ArrayList<Stores> stores = new ArrayList<>();
	
	@OneToMany(mappedBy="user")
	private ArrayList<Comments> comments = new ArrayList<>();
	
	@OneToMany(mappedBy="user")
	private ArrayList<Favorites> favorites = new ArrayList<>();
	
	@OneToMany(mappedBy="user")
	private ArrayList<GroupChatRooms> groupChatRooms = new ArrayList<>();
	
	@OneToMany(mappedBy="user")
	private ArrayList<GroupChatMsgs> msgs = new ArrayList<>();

	@OneToMany(mappedBy="user")
	private ArrayList<Carts> carts = new ArrayList<>();
}
