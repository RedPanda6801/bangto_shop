package com.example.banto.Entitys;

import java.time.LocalDateTime;
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
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Options {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="OPTION_NUM", nullable=false)
	private Integer optionNum;
	
	@Column(name="OPTION_INFO",  nullable=false)
	private String optionInfo;
	
	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name="ITEM_PK")
	private Items item;
	
	@OneToMany(mappedBy="option")
	private ArrayList<Carts> carts = new ArrayList<>();
}
