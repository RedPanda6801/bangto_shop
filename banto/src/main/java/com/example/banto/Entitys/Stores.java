package com.example.banto.Entitys;

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
public class Stores {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="STORE_NAME", nullable=false)
	private String name;
	
	@ManyToOne
	@JoinColumn(name="SELLER_PK")
	private Users user;
	
	@OneToMany(mappedBy="store")
	private ArrayList<Items> items = new ArrayList<>();
	
}
