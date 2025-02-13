package com.example.banto.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.banto.DAOs.ApplyDAO;
import com.example.banto.DTOs.ApplyDTO;
import com.example.banto.DTOs.ProcessDTO;
import com.example.banto.DTOs.StoreDTO;

@Service
public class ApplyService {
	@Autowired
	ApplyDAO applyDAO;
	
	public ApplyDTO getAuthInfo(Integer userId) throws Exception{
		if(userId == null) { 
			throw new Exception("권한 없음");
		}
		else {
			try {
				return applyDAO.getSellerAuth(userId);
			}catch(Exception e) {
				throw e;
			}
		}
	}
	
	public void applySellerAuth(Integer userId) throws Exception {
		if(userId == null) { 
			throw new Exception("권한 없음");
		}
		else {
			try {
				applyDAO.applySellerAuth(userId);
			}catch(Exception e) {
				throw e;
			}
		}
	}
	
	public void modify(Integer rootId, ProcessDTO dto) throws Exception {
		if(rootId == null ) {
			throw new Exception("권한 없음");
		}
		else {
			try {
				applyDAO.modify(rootId, dto);
			}catch(Exception e) {
				throw e;
			}
		}
	}
	
	public List<ApplyDTO> getApplyList(Integer rootId) throws Exception {
		if(rootId == null ) {
			throw new Exception("권한 없음");
		}
		else {
			try {
				return applyDAO.getApplyList(rootId);
			}catch(Exception e) {
				throw e;
			}
		}
	}
	
	public ApplyDTO getApply(Integer rootId, Integer userId) throws Exception {
		if(rootId == null ) {
			throw new Exception("권한 없음");
		}
		else {
			try {
				return applyDAO.getApply(rootId, userId);
			}catch(Exception e) {
				throw e;
			}
		}
	}
}
