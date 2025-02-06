package com.example.banto.DAOs;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.example.banto.DTOs.ItemDTO;
import com.example.banto.Entitys.Items;
import com.example.banto.Repositorys.ItemRepository;



@Component
public class ItemDAO {
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	AuthDAO authDAO;
	
	
	public List<ItemDTO> getItemsWithFilter(Integer userId, Integer storeId, Integer page) throws Exception{
		try {
			// 인증 유효 확인
			authDAO.auth(userId);
			// 판매자 권한 확인 필요...토큰으로?
			// storeId로 가져오기
			Pageable pageable = PageRequest.of(page-1, 20, Sort.by("id").ascending());
			Page<Items>items = itemRepository.getItemsByStoreId(storeId, pageable);
			List<ItemDTO>itemList = new ArrayList<ItemDTO>();
			for(Items item : items) {
				ItemDTO dto = ItemDTO.toDTO(item);
				itemList.add(dto);
			}
			return itemList;
		}catch(Exception e) {
			throw e;
		}
	}
}
