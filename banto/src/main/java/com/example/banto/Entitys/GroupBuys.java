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
public class GroupBuys {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "START_DATE", nullable=false)
	private LocalDateTime startDate;
	
	@Column(name="END_DATE", nullable=false)
	private LocalDateTime endDate;
	
	@OneToMany(mappedBy="event")
	private ArrayList<GroupBuyItems> items = new ArrayList<>();
	
	
}
