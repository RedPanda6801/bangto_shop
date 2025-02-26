package com.example.banto.Entitys;
import java.util.List;

import com.example.banto.DTOs.ItemDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Items {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @Column(name="TITLE", nullable=false)
    private String title;
    
	@Column(name="CATEGORY",  nullable=false)
	private Integer category;
    
    @Column(name="PRICE", nullable=false)
    private Integer price;
    
    @Column(name="CONTENT", nullable=false)
    private String content;
    
    @Column(name="IMG", nullable=true)
    private String img;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="STORE_PK")
    private Stores store;
    
    @OneToMany(mappedBy="item", cascade = CascadeType.ALL)  // 추가: cascade 설정
    private List<QNAs> qnas;
    
    @JsonIgnore
    @OneToMany(mappedBy="item", cascade = CascadeType.ALL)  // 추가: cascade 설정
    private List<Comments> comments;
    
    @OneToMany(mappedBy="item", cascade = CascadeType.ALL)  // 추가: cascade 설정
    private List<Options> options;
    
    @JsonIgnore
    @OneToMany(mappedBy="item", cascade = CascadeType.ALL)  // 추가: cascade 설정
    private List<Favorites> favorites;
    
    @OneToMany(mappedBy="item")  // 추가: cascade 설정
    private List<GroupBuyItems> events;
    
    @JsonIgnore
    @OneToMany(mappedBy="item", cascade = CascadeType.ALL)  // 추가: cascade 설정
    private List<Carts> carts;
    
    public static Items toEntity(ItemDTO dto) {
    	return Items.builder()
    			.title(dto.getTitle())
    			.price(dto.getPrice())
    			.content(dto.getContent())
    			.category(dto.getCategory())
    			.build();
    }
}
