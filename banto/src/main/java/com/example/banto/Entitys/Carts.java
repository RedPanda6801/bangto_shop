package com.example.banto.Entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SequenceGenerator(
		name = "cart_seq",
		sequenceName = "cart_seq",
		allocationSize = 1
)
public class Carts {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_seq")
	private Integer id;
	
	@Column(name="AMOUNT", nullable=false)
	private Integer amount;

	@ManyToOne
	@JoinColumn(name="BUYER_PK")
	private Users user;
	
	@ManyToOne
	@JoinColumn(name="ITEM_PK")
	private Items item;
	
	@ManyToOne
	@JoinColumn(name="OPTION_PK")
	private Options option;
	
	private Integer totalPrice;
}
