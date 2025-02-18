package com.example.banto.DTOs;

import java.time.LocalDateTime;

import com.example.banto.Entitys.Items;
import com.example.banto.Entitys.QNAs;
import com.example.banto.Entitys.Users;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QNADTO {
    private Integer id;
    
    private String qContent;
    
    private String aContent;

    private LocalDateTime qWriteDate;
    
    private LocalDateTime aWriteDate;
    
    private Integer itemPk;
    
    private Integer userPk;
    
    private Integer storePk;
    
    private Users user;

    private Items item;
    
    public static QNADTO toDTO(QNAs entity) {
        return QNADTO.builder()
                .id(entity.getId())
                .qContent(entity.getQContent())
                .aContent(entity.getAContent())
                .qWriteDate(entity.getQWriteDate())
                .aWriteDate(entity.getAWriteDate())
                .itemPk(entity.getItem().getId())
                .userPk(entity.getUser().getId())
                .build();
    }
}
