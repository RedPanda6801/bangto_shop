package com.example.banto.DTOs;

import java.util.List;

import com.example.banto.Entitys.Items;
import com.example.banto.Entitys.Sellers;
import com.example.banto.Entitys.Stores;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreDTO {
   
    private Integer id;

    private String name;

    private String sellerName;

    private String busiNum;
    
    private Sellers seller;

    private List<Items> items;
    
    public static StoreDTO toDTO(Stores entity) {
        return StoreDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .sellerName(entity.getSeller().getUser().getName())
                .busiNum(entity.getBusiNum())
                .seller(null)
                .items(entity.getItems())
                .build();
    }
    public StoreDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    public StoreDTO(Integer id, String name, String busiNum) {
        this.id = id;
        this.name = name;
        this.busiNum = busiNum;
    }
}
