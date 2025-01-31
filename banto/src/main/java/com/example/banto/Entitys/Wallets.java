package com.example.banto.Entitys;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Wallets {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="CASH", nullable=true)
	private Integer cash = 0;
	
	@Column(name="CASH_BACK", nullable=true)
	private Integer cashBack = 0;
	
	@OneToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="OWNER_PK")
	private Users user;
	
}
