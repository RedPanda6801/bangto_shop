package com.example.banto.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.banto.DAOs.ItemDAO;
import com.example.banto.DTOs.ItemDTO;
import com.example.banto.DTOs.OptionDTO;
import com.example.banto.DTOs.ResponseDTO;

@Service
public class ItemService {
	@Autowired
	ItemDAO itemDAO;
	
	public ResponseDTO getAllItemList(Integer page) throws Exception {
		try {
			if(page == null || page < 1) {
				throw new Exception("페이지 입력 오류");
		}else {
				return itemDAO.getAllItemList(page);
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	public ResponseDTO getFilterdItemList(ItemDTO dto) throws Exception {
		try {
			return itemDAO.getFilterdItemList(dto);
		}catch(Exception e) {
			throw e;
		}
	}
	
	public ResponseDTO getItemList(Integer storeId, Integer page) throws Exception {
		try {
			if(page == null || page < 1) {
				throw new Exception("페이지 입력 오류");
		}else {
				return itemDAO.getItemList(storeId, page);
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	public ResponseDTO getItemListByTitle(String title, Integer page) throws Exception {
		try {
			if(page == null || page < 1) {
				throw new Exception("페이지 입력 오류");
		}else {
				return itemDAO.getItemListByTitle(title, page);
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	public ResponseDTO getItemListByStoreName(String storeName, Integer page) throws Exception {
		try {
			if(page == null || page < 1) {
				throw new Exception("페이지 입력 오류");
		}else {
				return itemDAO.getItemListByStoreName(storeName, page);
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	public ResponseDTO getItemListByCategory(String category, Integer page) throws Exception {
		try {
			if(page == null || page < 1) {
				throw new Exception("페이지 입력 오류");
		}else {
				return itemDAO.getItemListByCategory(category, page);
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	public ResponseDTO getItemDetail(Integer itemId) throws Exception {
		try {
			return itemDAO.getItemDetail(itemId);
		}catch(Exception e) {
			throw e;
		}
	}
	
	public void addItem(ItemDTO itemDTO, List<MultipartFile> files) throws Exception {
		try {
			itemDAO.addItem(itemDTO, files);
		}catch(Exception e) {
			throw e;
		}
	}
	
	public void modifyItem(ItemDTO itemDTO) throws Exception {
		try {
			itemDAO.modifyItem(itemDTO);
		}catch(Exception e) {
			throw e;
		}
	}
	
	public void modifyItemOption(OptionDTO optionDTO) throws Exception {
		try {
			itemDAO.modifyItemOption(optionDTO);
		}catch(Exception e) {
			throw e;
		}
	}
}
