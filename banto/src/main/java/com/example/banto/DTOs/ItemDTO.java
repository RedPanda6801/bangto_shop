package com.example.banto.DTOs;
import java.util.List;

import com.example.banto.Entitys.Carts;
import com.example.banto.Entitys.Comments;
import com.example.banto.Entitys.Favorites;
import com.example.banto.Entitys.GroupBuyItems;
import com.example.banto.Entitys.Items;
import com.example.banto.Entitys.Options;
import com.example.banto.Entitys.QNAs;
import com.example.banto.Entitys.Stores;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDTO {

    private Integer id;

    private String title;

	private Integer category;

    private Integer price;
    
    private String content;

    private String img;

    private Integer amount;
    
    private Integer storePk;
    
    private Integer star;
    
    private List<QNAs> qnas;

    private List<Comments> comments;

    private List<Options> options;
  
    private List<Favorites> favorites;
    
    private List<GroupBuyItems> events;

    private List<Carts> carts;
    
    public ItemDTO(Integer id, String title, Integer category, Integer price, 
        String content, String img, Integer amount, Integer star) {
		 this.id = id;
		 this.title = title;
		 this.category = category;
		 this.price = price;
		 this.content = content;
		 this.img = img;
		 this.amount = amount;
		 this.star = star;
	}
    
    public static ItemDTO toDTO(Items entity) {
        return ItemDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .category(entity.getCategory())
                .price(entity.getPrice())
                .content(entity.getContent())
                .img(entity.getImg())
                .amount(entity.getAmount())
                .options(entity.getOptions())
                .star(entity.getFavorites().size())
                .qnas(entity.getQnas())
                .comments(entity.getComments())
                .options(entity.getOptions())
                .build();
    }


}
