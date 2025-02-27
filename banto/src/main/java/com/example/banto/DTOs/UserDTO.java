package com.example.banto.DTOs;

import java.time.LocalDateTime;

import com.example.banto.Entitys.Users;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Integer id;
    private String email;
    private String pw;
    private String name;
    private String addr;
    private String phone;
    private LocalDateTime regDate;
    private Boolean snsAuth;

    public static UserDTO toDTO(Users entity) {
    	entity.setEmail(entity.getEmail().replace("@kakao", ""));
        return UserDTO.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .pw(entity.getPw())
                .name(entity.getName())
                .addr(entity.getAddr())
                .phone(entity.getPhone())
                .regDate(entity.getRegDate())
                .snsAuth(entity.getSnsAuth())
                .build();
    }
}