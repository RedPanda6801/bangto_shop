package com.example.banto.DAOs;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.banto.DTOs.QNADTO;
import com.example.banto.Entitys.Items;
import com.example.banto.Entitys.QNAs;
import com.example.banto.Entitys.Users;
import com.example.banto.Repositorys.ItemRepository;
import com.example.banto.Repositorys.QNARepository;

import jakarta.transaction.Transactional;

@Component
public class QNADAO {
	@Autowired
	AuthDAO authDAO;
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	QNARepository qnaRepository;
	
	
	@Transactional
	public void addQNA(Integer userId, QNADTO qnaDTO) throws Exception{
		try {
			Users user = authDAO.auth(userId);
			
			Optional<Items> itemOpt = itemRepository.findById(qnaDTO.getItemPk()); 
			if(itemOpt.isEmpty()) {
				throw new Exception("물품 정보 오류");
			}
			else {
				Items item = itemOpt.get();
				if(item.getStore().getSeller().getUser().getId() == userId) {
					throw new Exception("판매자 본인 등록 불가");
				}
				else {
					QNAs qna = new QNAs();
					qna.setUser(user);
					qna.setQContent(qnaDTO.getQContent());
					qna.setItem(itemOpt.get());
					qnaRepository.save(qna);
				}
			}

			
		}catch(Exception e) {
			throw e;
		}
	}
}
