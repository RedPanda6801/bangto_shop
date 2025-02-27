package com.example.banto.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDTO {
	Object content;
	// 하나의 객체로
	// 모든 메소드에 적용
	long totalElements;
	Integer totalPages;
	Integer size;
}
