package com.example.banto.Entitys;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Carts {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="AMMOUNT", nullable=false)
	private Integer ammount;

	@ManyToOne
	@JoinColumn(name="BUYER_PK")
	private Users user;
	
	@ManyToOne
	@JoinColumn(name="ITEM_PK")
	private Items item;
	
	@ManyToOne
	@JoinColumn(name="OPTION_PK")
	private Options option;
}
