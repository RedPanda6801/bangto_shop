package com.example.banto.Entitys;

import java.time.LocalDateTime;
import java.util.ArrayList;

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
		name = "favorite_seq",
		sequenceName = "favorite_seq",
		allocationSize = 1
)
public class Favorites {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "favorite_seq")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="USER_PK")
	private Users user;
	
	@ManyToOne
	@JoinColumn(name="ITEM_PK")
	private Items item;
	
	public Favorites(Users user, Items item) {
		this.user = user;
		this.item = item;
	}
}
