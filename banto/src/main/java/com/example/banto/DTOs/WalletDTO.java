package com.example.banto.DTOs;

import com.example.banto.Entitys.Users;
import com.example.banto.Entitys.Wallets;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletDTO {
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="CASH", nullable=true)
    private Integer cash;  // 기본값 0 설정

    @Column(name="CASH_BACK", nullable=true)
    private Integer cashBack;  // 기본값 0 설정

    @OneToOne(cascade = CascadeType.ALL)  // 모든 cascade 동작을 원한다면 CascadeType.ALL을 사용하는 것이 좋습니다.
    @JoinColumn(name = "OWNER_PK")
    private Users user;
    
    // 관리자가 조정할 지갑의 PK
    @JsonIgnore
    private Integer walletPk;
    
    public static WalletDTO toDTO(Wallets entity) {
        return WalletDTO.builder()
                .id(entity.getId())
                .cash(entity.getCash())
                .cashBack(entity.getCashBack())
                .user(entity.getUser())
                .build();
    }
}