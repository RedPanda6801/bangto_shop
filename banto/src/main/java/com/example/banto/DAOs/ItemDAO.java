package com.example.banto.DAOs;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.example.banto.DTOs.ItemDTO;
import com.example.banto.Entitys.Items;
import com.example.banto.Entitys.Options;
import com.example.banto.Entitys.Stores;
import com.example.banto.Repositorys.ItemRepository;
import com.example.banto.Repositorys.OptionRepository;
import com.example.banto.Repositorys.StoreRepository;

import jakarta.transaction.Transactional;



@Component
public class ItemDAO {
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	AuthDAO authDAO;
	@Autowired
	StoreRepository storeRepository;
	@Autowired
	OptionRepository optionRepository;
	
	public List<ItemDTO> getItemList(Integer userId, Integer storeId, Integer page) throws Exception{
		try {
			// 인증 유효 확인
			if(userId != -1) {
				authDAO.auth(userId);
			}
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
	
	public ItemDTO getItemDetail(Integer userId, Integer itemId) throws Exception{
		try {
			// 인증 유효 확인
			if(userId != -1) {
				authDAO.auth(userId);
			}
			Optional<Items> item = itemRepository.findById(itemId);
			if(item.isEmpty()) {
				throw new Exception("물건 조회 오류");
			}else {
				ItemDTO dto = ItemDTO.toDTO(item.get());
				return dto;
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public void addItem(Integer userId, ItemDTO itemDTO) throws Exception{
		try {
			// 인증 유효 확인
			authDAO.auth(userId);
			Optional<Stores> store = storeRepository.findById(itemDTO.getStorePk());
			if(store.isEmpty()) {
				throw new Exception("매장 조회 오류");
			}else {
				Items item = Items.toEntity(itemDTO);
				item.setStore(store.get());
				Items newItem = itemRepository.save(item);
				
				for(Options option : itemDTO.getOptions()) {
					option.setAddPrice(option.getAddPrice());
					option.setItem(newItem); // 연관 관계 설정
	                optionRepository.save(option); // 개별적으로 저장
				}
			}
		}catch(Exception e) {
			throw e;
		}
	}
}
