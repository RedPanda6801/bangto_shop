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
public class Stores {
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="STORE_NAME", nullable=false)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)  // 'Users' 삭제 시 관련 'Stores'도 삭제되도록 설정
    @JoinColumn(name="SELLER_PK")
    private Sellers seller;

    @OneToMany(mappedBy="store", cascade = CascadeType.ALL)  // 'Items' 엔티티에서 'store' 필드를 기준으로 관계를 매핑
    private List<Items> items;
}
