package com.example.banto.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.banto.DAOs.QNADAO;
import com.example.banto.DTOs.FavoriteDTO;
import com.example.banto.DTOs.QNADTO;
import com.example.banto.DTOs.ResponseDTO;

@Service
public class QNAService {
	@Autowired
	QNADAO qnaDAO;
	
	public void addQNA(Integer userId, QNADTO qnaDTO) throws Exception {
		try {
			if(qnaDTO.getQContent() == null || qnaDTO.getQContent() == "" || qnaDTO.getItemPk() == null) {
				throw new Exception("필수 정보 오류");
			}
			else {
				qnaDAO.addQNA(userId, qnaDTO);
			}
		}catch(Exception e) {
		 throw e;
		}
	}
	
	public void addSellerAnswer(Integer userId, QNADTO qnaDTO) throws Exception {
		try {
			if(qnaDTO.getAContent() == null || qnaDTO.getAContent() == "" || qnaDTO.getItemPk() == null) {
				throw new Exception("필수 정보 오류");
			}
			else {
				qnaDAO.addSellerAnswer(userId, qnaDTO);
			}
		}catch(Exception e) {
		 throw e;
		}
	}
	
	public ResponseDTO getMyList(Integer userId, Integer page) throws Exception {
		try {
			
			if(page == null || page < 1) {
				throw new Exception("페이지 입력 오류");
			}else {
				return qnaDAO.getMyList(userId, page);
			}
		}catch(Exception e) {
		 throw e;
		}
	}
	
	public ResponseDTO getListByStore(QNADTO qnaDTO, Integer page) throws Exception {
		try {
			if(qnaDTO.getStorePk() == null) {
				throw new Exception("매장 정보 오류");
			}
			else if(page == null || page < 1) {
				throw new Exception("페이지 입력 오류");
			}else {
				return qnaDAO.getListByStore(qnaDTO, page);
			}
		}catch(Exception e) {
		 throw e;
		}
	}
	
	public ResponseDTO getQnaDetail(Integer userId, QNADTO qnaDTO) throws Exception {
		try {
			if(qnaDTO.getId() == null){
				throw new Exception("필수 정보 오류");
			}else {
				return qnaDAO.getQnaDetail(userId, qnaDTO);
			}
		}catch(Exception e) {
		 throw e;
		}
	}
}
