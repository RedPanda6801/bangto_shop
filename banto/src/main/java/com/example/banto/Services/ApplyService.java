package com.example.banto.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.banto.DAOs.ApplyDAO;
import com.example.banto.DTOs.ApplyDTO;
import com.example.banto.DTOs.ProcessDTO;
import com.example.banto.DTOs.ResponseDTO;

@Service
public class ApplyService {
	@Autowired
	ApplyDAO applyDAO;
	
	public ResponseDTO getAuthInfo(Integer userId) throws Exception{
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
	
	public void modify(ProcessDTO dto) throws Exception {
		try {
			applyDAO.modify(dto);
		}catch(Exception e) {
			throw e;
		}
	}
	
	public ResponseDTO getApplyList(Integer page) throws Exception {
		try {
			return applyDAO.getApplyList(page);
		}catch(Exception e) {
			throw e;
		}
	}
	
	public ResponseDTO getApply(Integer userId) throws Exception {
		try {
			return applyDAO.getApply(userId);
		}catch(Exception e) {
			throw e;
		}
	}
}
