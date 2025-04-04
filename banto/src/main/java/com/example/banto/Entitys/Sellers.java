package com.example.banto.Entitys;

import com.example.banto.DTOs.SellerDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SequenceGenerator(
		name = "seller_seq",
		sequenceName = "seller_seq",
		allocationSize = 1
)
public class Sellers {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seller_seq")
	private Integer id;

	@ToString.Exclude  // 필드에 적용
	@OneToMany(mappedBy="seller", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Stores> stores;

	@JsonIgnore
	@EqualsAndHashCode.Exclude
	@ToString.Exclude  // 필드에 적용
	@OneToOne
	@JoinColumn(name = "USER_PK")
	private Users user;

	public static Sellers toEntity(SellerDTO dto) {
		return Sellers.builder()
				.id(dto.getId())
				.user(dto.getUser())
				.build();
	}
}
