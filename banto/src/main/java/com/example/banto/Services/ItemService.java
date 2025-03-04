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
	
	public ResponseDTO getItemList(Integer userId, Integer storeId, Integer page) throws Exception {
		try {
			if(page == null || page < 1) {
				throw new Exception("페이지 입력 오류");
		}else {
				return itemDAO.getItemList(userId, storeId, page);
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	public ResponseDTO getItemListByTitle(Integer userId, String title, Integer page) throws Exception {
		try {
			if(page == null || page < 1) {
				throw new Exception("페이지 입력 오류");
		}else {
				return itemDAO.getItemListByTitle(userId, title, page);
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	public ResponseDTO getItemListByStoreName(Integer userId, String storeName, Integer page) throws Exception {
		try {
			if(page == null || page < 1) {
				throw new Exception("페이지 입력 오류");
		}else {
				return itemDAO.getItemListByStoreName(userId, storeName, page);
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	public ResponseDTO getItemListByCategory(Integer userId, String category, Integer page) throws Exception {
		try {
			if(page == null || page < 1) {
				throw new Exception("페이지 입력 오류");
		}else {
				return itemDAO.getItemListByCategory(userId, category, page);
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	public ResponseDTO getItemDetail(Integer userId, Integer itemId) throws Exception {
		try {
			return itemDAO.getItemDetail(userId, itemId);
		}catch(Exception e) {
			throw e;
		}
	}
	
	public void addItem(Integer userId, ItemDTO itemDTO, List<MultipartFile> files) throws Exception {
		try {
			itemDAO.addItem(userId, itemDTO, files);
		}catch(Exception e) {
			throw e;
		}
	}
	
	public void modifyItem(Integer userId, ItemDTO itemDTO) throws Exception {
		try {
			itemDAO.modifyItem(userId, itemDTO);
		}catch(Exception e) {
			throw e;
		}
	}
	
	public void modifyItemOption(Integer userId, OptionDTO optionDTO) throws Exception {
		try {
			itemDAO.modifyItemOption(userId, optionDTO);
		}catch(Exception e) {
			throw e;
		}
	}
}
