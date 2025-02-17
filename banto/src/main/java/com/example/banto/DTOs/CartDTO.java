package com.example.banto.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDTO {
	private Integer cartPk;
	private Integer itemPk;
	private Integer amount;
	private Integer optionPk;
	private Integer userPk;
}
