package com.example.banto.DTOs;

import java.time.LocalDateTime;

import com.example.banto.Entitys.Items;
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
    
    @JsonProperty("qContent") 
    private String qContent;
    
    private String aContent;

    private LocalDateTime qWriteDate;
    
    private LocalDateTime aWriteDate;
    
    private Integer itemPk;
    
    private Integer userPk;
    
    private Users user;

    private Items item;
}
