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
    private Boolean sellerState;
    private LocalDateTime regDate;
    private Boolean snsAuth;

    public static UserDTO toDto(Users entity) {
        return UserDTO.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .pw(entity.getPw())
                .name(entity.getName())
                .addr(entity.getAddr())
                .phone(entity.getPhone())
                .sellerState(entity.getSellerState())
                .regDate(entity.getRegDate())
                .snsAuth(entity.getSnsAuth())
                .build();
    }
}