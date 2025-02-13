package com.example.banto.DTOs;

import java.time.LocalDateTime;

import com.example.banto.Entitys.ApplyType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessDTO {
	private Integer sellerAuthPk;
    private String process;
}
