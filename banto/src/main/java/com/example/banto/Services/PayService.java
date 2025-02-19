package com.example.banto.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.banto.DAOs.PayDAO;
import com.example.banto.DTOs.PayDTO;
import com.example.banto.DTOs.SoldItemDTO;

@Service
public class PayService {
	@Autowired
	PayDAO payDAO;

	public void payCart(Integer userId, PayDTO dto) throws Exception{
		if(userId == null) { 
			throw new Exception("권한 없음");
		}
		else {
			try {
				payDAO.payCart(userId, dto);
			}catch(Exception e) {
				throw e;
			}
		}
	}
	
	public List<SoldItemDTO> getPayList(Integer userId, Integer page) throws Exception{
		if(userId == null) { 
			throw new Exception("권한 없음");
		}
		else {
			try {
				return payDAO.getPayList(userId, page);
			}catch(Exception e) {
				throw e;
			}
		}
	}
	
	public void modifySoldItem(Integer userId, SoldItemDTO dto) throws Exception{
		if(userId == null) { 
			throw new Exception("권한 없음");
		}
		else {
			try {
				payDAO.modifySoldItem(userId, dto);
			}catch(Exception e) {
				throw e;
			}
		}
	}
	
	public List<SoldItemDTO> getSoldList(Integer userId, Integer storeId, Integer page) throws Exception{
		if(userId == null) { 
			throw new Exception("권한 없음");
		}
		else {
			try {
				return payDAO.getSoldList(userId, storeId, page);
			}catch(Exception e) {
				throw e;
			}
		}
	}
}
