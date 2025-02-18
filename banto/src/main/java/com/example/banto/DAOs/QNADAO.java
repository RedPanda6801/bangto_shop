package com.example.banto.DAOs;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.example.banto.DTOs.QNADTO;
import com.example.banto.Entitys.Items;
import com.example.banto.Entitys.QNAs;
import com.example.banto.Entitys.Sellers;
import com.example.banto.Entitys.Stores;
import com.example.banto.Entitys.Users;
import com.example.banto.Repositorys.ItemRepository;
import com.example.banto.Repositorys.QNARepository;
import com.example.banto.Repositorys.SellerRepository;
import com.example.banto.Repositorys.StoreRepository;

import jakarta.transaction.Transactional;

@Component
public class QNADAO {
	@Autowired
	AuthDAO authDAO;
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	QNARepository qnaRepository;
	@Autowired
	SellerRepository sellerRepository;
	@Autowired
	StoreRepository storeRepository;
	
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
	
	@Transactional
	public void addSellerAnswer(Integer userId, QNADTO qnaDTO) throws Exception{
		try {
			authDAO.auth(userId);	
			Optional<QNAs> qnaOpt = qnaRepository.findById(qnaDTO.getId());
			Optional<Sellers> sellerOpt = sellerRepository.findByUser_Id(userId);
			if(qnaOpt.isEmpty()) {
				throw new Exception("QNA 정보 오류");
			}else if(sellerOpt.isEmpty()) {
				// 판매자만 가능
				throw new Exception("판매자 인증 오류");
			}
			else {
				Sellers seller = sellerOpt.get();
				QNAs qna = qnaOpt.get();
				if(qna.getItem().getStore().getSeller().getId() != seller.getId()) {
					throw new Exception("판매자 물품 불일치");
				}
		        // 현재 시간 가져오기
		        LocalDateTime now = LocalDateTime.now();  

				qna.setAContent(qnaDTO.getAContent());
				qna.setAWriteDate(now);
				qnaRepository.save(qna);
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	public List<QNADTO> getMyList(Integer userId, Integer page) throws Exception{
		try {
			authDAO.auth(userId);
			List<QNAs> qnaList = qnaRepository.findAllByUserId(userId);
			List<QNADTO> dtos = new ArrayList<>();
			for(QNAs qna : qnaList) {
				QNADTO dto = QNADTO.toDTO(qna);
				dtos.add(dto);
			}
			return dtos;
		}catch(Exception e) {
			throw e;
		}
	}
	
	public List<QNADTO> getListByStore(QNADTO qnaDTO, Integer page) throws Exception{
		try {
			authDAO.auth(qnaDTO.getUserPk());
			
			Optional<Stores> storeOpt = storeRepository.findById(qnaDTO.getStorePk());
			if(storeOpt.isEmpty()) {
				throw new Exception("매장 정보 오류");
			}else if(storeOpt.get().getSeller().getUser().getId() != qnaDTO.getUserPk()) {
				throw new Exception("본인 매장 불일치");
			}else {
				Pageable pageable = PageRequest.of(page-1, 20, Sort.by("id").ascending());
				List<QNAs> qnaList = qnaRepository.findAllByStore(qnaDTO.getStorePk(), pageable);
				List<QNADTO> dtos = new ArrayList<>();
				for(QNAs qna : qnaList) {
					QNADTO dto = QNADTO.toDTO(qna);
					dtos.add(dto);
				}
				return dtos;
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	public QNADTO getQnaDetail(Integer userId, QNADTO qnaDTO) throws Exception{
		try {
			authDAO.auth(userId);
			Optional<QNAs> qnaOpt = qnaRepository.findById(qnaDTO.getId());
			if(qnaOpt.isEmpty()) {
				return null;
			}
			return QNADTO.toDTO(qnaOpt.get());
		}catch(Exception e) {
			throw e;
		}
	}
}
