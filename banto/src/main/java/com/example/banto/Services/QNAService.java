package com.example.banto.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.banto.DAOs.QNADAO;
import com.example.banto.DTOs.QNADTO;

@Service
public class QNAService {
	@Autowired
	QNADAO qnaDAO;
	
	public void addQNA(Integer userId, QNADTO qnaDTO) throws Exception {
		try {
			System.out.println(qnaDTO.getQContent());
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
}
