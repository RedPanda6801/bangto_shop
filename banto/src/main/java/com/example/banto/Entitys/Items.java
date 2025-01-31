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
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Items {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="TITLE", nullable=false)
	private String title;
	
	// 카테고리는 추후에 정한 카테고리대로 enum 세팅할 예정
	@Column(name="CATEGORY",  nullable=false)
	private Integer category;
	
	@Column(name="PRICE", nullable=false)
	private Integer price;
	
	@Column(name="CONTENT", nullable=false)
	private String content;
	
	@Column(name="IMG", nullable=true)
	private String img;
	
	@Column(name="AMMOUNT", nullable=false)
	private Integer ammount = 0;
	
	// 참조 객체
	@ManyToOne
	@JoinColumn(name="STORE_PK")
	private Stores store;
	
	@OneToMany(mappedBy="item")
	private ArrayList<QNAs> qnas = new ArrayList<>();
	
	@OneToMany(mappedBy="item")
	private ArrayList<Comments> comments = new ArrayList<>();
	
	@OneToMany(mappedBy="item")
	private ArrayList<Options> options = new ArrayList<>();
	
	@OneToMany(mappedBy="item")
	private ArrayList<Favorites> favorites = new ArrayList<>();
	
	@OneToMany(mappedBy="item")
	private ArrayList<GroupBuyItems> events = new ArrayList<>();
	
	@OneToMany(mappedBy="item")
	private ArrayList<Carts> carts = new ArrayList<>();
}
