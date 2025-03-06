package com.example.banto.Entitys;

import java.util.List;

import com.example.banto.DTOs.SellerDTO;

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
public class Sellers {
	 @Id
	    @Column(name="ID")
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Integer id;
	    
	    @OneToMany(mappedBy="seller", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	    private List<Stores> stores;
	    
	    @OneToOne
	    @JoinColumn(name = "USER_PK")
	    private Users user;
	    
	    public static Sellers toEntity(SellerDTO dto) {
	        return Sellers.builder()
	                .id(dto.getId())
	                .user(dto.getUser())
	                .build();
	    }
}
