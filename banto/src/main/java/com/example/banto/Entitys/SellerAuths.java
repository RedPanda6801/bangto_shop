package com.example.banto.Entitys;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class SellerAuths {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="AUTH", nullable=false)
	private ApplyType Auth = ApplyType.Processing;
	
	@Column(name="APPLY_DATE", columnDefinition = "date default sysdate")
	private LocalDateTime applyDate;
	
	@Column(name="SIGN_DATE", nullable=true)
	private LocalDateTime signDate;
	
	@ManyToOne
	@JoinColumn(name="USER_PK")
	private Users user;
}
