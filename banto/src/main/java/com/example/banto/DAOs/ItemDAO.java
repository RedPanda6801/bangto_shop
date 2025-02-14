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
import com.example.banto.DTOs.OptionDTO;
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
	
	public List<ItemDTO> getAllItemList(Integer page) throws Exception{
		try {
			Pageable pageable = PageRequest.of(page-1, 20, Sort.by("id").ascending());
			Page<Items>items = itemRepository.findAll(pageable);
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
	
	@Transactional
	public void modifyItem(Integer userId, ItemDTO dto) throws Exception{
		try {
			// 인증 유효 확인
			if(userId != -1) {
				authDAO.auth(userId);
			}
			Optional<Stores> store = storeRepository.findById(dto.getStorePk());
			if(store.isEmpty()) {
				throw new Exception("매장 조회 오류");
			}else {
				List<Items> itemList = store.get().getItems();
				for(Items item : itemList) {
					if(item.getId() == dto.getId()) {
						// 수정 로직
						item.setTitle((dto.getTitle() != null && !dto.getTitle().equals("")) ?
								dto.getTitle() : item.getTitle());
						item.setCategory((dto.getCategory() != null && !dto.getCategory().equals("")) ?
								dto.getCategory() : item.getCategory());
						item.setImg((dto.getImg() != null && !dto.getImg().equals("")) ?
								dto.getImg() : item.getImg());
						item.setContent((dto.getContent() != null && !dto.getContent().equals("")) ?
								dto.getContent() : item.getContent());
						item.setAmount((dto.getAmount() != null && !dto.getAmount().equals("")) ?
								dto.getAmount() : item.getAmount());
						itemRepository.save(item);
					}
				}
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public void modifyItemOption(Integer userId, OptionDTO dto) throws Exception{
		try {
			// 인증 유효 확인
			if(userId != -1) {
				authDAO.auth(userId);
			}
			Optional<Items> item = itemRepository.findById(dto.getItemPk());
			if(item.isEmpty()) {
				throw new Exception("매장 조회 오류");
			}else {
				List<Options> optionList = item.get().getOptions();
				for(Options option : optionList) {
					if(option.getId() == dto.getId()) {
						// 수정 로직
						option.setAddPrice((dto.getAddPrice() != null && !dto.getAddPrice().equals("")) ?
								dto.getAddPrice() : option.getAddPrice());
						option.setOptionInfo((dto.getOptionInfo() != null && !dto.getOptionInfo().equals("")) ?
								dto.getOptionInfo() : option.getOptionInfo());
						optionRepository.save(option);
					}
				}
			}
		}catch(Exception e) {
			throw e;
		}
	}
}
