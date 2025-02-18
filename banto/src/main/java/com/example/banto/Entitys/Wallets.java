package com.example.banto.Entitys;

import java.util.List;

import com.example.banto.DTOs.WalletDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Wallets {
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="CASH", nullable=true)
    private Integer cash = 0;  // 기본값 0 설정

    @Column(name="CASH_BACK", nullable=true)
    private Integer cashBack = 0;  // 기본값 0 설정

    @OneToOne//(cascade = CascadeType.ALL)  // 모든 cascade 동작을 원한다면 CascadeType.ALL을 사용하는 것이 좋습니다.
    @JoinColumn(name = "OWNER_PK")
    private Users user;
    
    public static Wallets toEntity(WalletDTO dto) {
        return Wallets.builder()
                .id(dto.getId())
                .cash((dto.getCash() != null) ? dto.getCash() : 0)
                .cashBack((dto.getCashBack() != null) ? dto.getCashBack() : 0)
                .user(dto.getUser())
                .build();
    }
}