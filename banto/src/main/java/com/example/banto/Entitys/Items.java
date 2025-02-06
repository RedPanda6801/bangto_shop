package com.example.banto.Entitys;
import java.util.List;

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
    
    @Column(name="AMMOUNT", nullable=false)
    private Integer ammount = 0;
    
    // 참조 객체
    @ManyToOne
    @JoinColumn(name="STORE_PK")
    private Stores store;
    
    @OneToMany(mappedBy="item", cascade = CascadeType.ALL)  // 추가: cascade 설정
    private List<QNAs> qnas;
    
    @OneToMany(mappedBy="item", cascade = CascadeType.ALL)  // 추가: cascade 설정
    private List<Comments> comments;
    
    @OneToMany(mappedBy="item", cascade = CascadeType.ALL)  // 추가: cascade 설정
    private List<Options> options;
    
    @OneToMany(mappedBy="item", cascade = CascadeType.ALL)  // 추가: cascade 설정
    private List<Favorites> favorites;
    
    @OneToMany(mappedBy="item", cascade = CascadeType.ALL)  // 추가: cascade 설정
    private List<GroupBuyItems> events;
    
    @OneToMany(mappedBy="item", cascade = CascadeType.ALL)  // 추가: cascade 설정
    private List<Carts> carts;
}
