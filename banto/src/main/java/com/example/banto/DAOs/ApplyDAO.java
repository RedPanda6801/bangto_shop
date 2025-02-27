package com.example.banto.DAOs;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.example.banto.Entitys.ApplyType;
import com.example.banto.Entitys.SellerAuths;
import com.example.banto.Entitys.Sellers;
import com.example.banto.Entitys.Users;
import com.example.banto.Repositorys.ApplyRepository;
import com.example.banto.Repositorys.SellerRepository;
import com.example.banto.Repositorys.UserRepository;

import jakarta.transaction.Transactional;

import com.example.banto.Configs.EnvConfig;
import com.example.banto.DTOs.ApplyDTO;
import com.example.banto.DTOs.ProcessDTO;

@Component
public class ApplyDAO {
	@Autowired
	ApplyRepository applyRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	SellerRepository sellerRepository;
	@Autowired
	AuthDAO authDAO;
	@Autowired
	EnvConfig envConfig;
	
	public List<ApplyDTO> getSellerAuth(Integer userId) throws Exception{
		try {
			// 인증 유효 확인
			authDAO.auth(userId);
			List<SellerAuths> sellerAuths = applyRepository.findAllByUserId(userId);
			if(sellerAuths.isEmpty()) {
				throw new Exception("등록된 판매자가 아닙니다.");
			}
			else {
				List<ApplyDTO> applyList = new ArrayList<ApplyDTO>();
				for(SellerAuths auth : sellerAuths) {					
					ApplyDTO dto = ApplyDTO.toDTO(auth);
					applyList.add(dto);
				}
				return applyList;
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public void applySellerAuth(Integer userId) throws Exception {
		try {
			// 인증 유효 확인
			Users user = authDAO.auth(userId);
			// 판매자 가져오기
			Optional<Sellers> seller = sellerRepository.findByUser_Id(userId);
			Optional<SellerAuths> processing = applyRepository.findProcessingAuthByUserId(userId);
			if(processing.isEmpty() && seller.isEmpty()) {
				SellerAuths apply = new SellerAuths();
				apply.setUser(user);
				apply.setAuth(ApplyType.Processing);
				applyRepository.save(apply);
			}
			else if(processing.isPresent()) {
				throw new Exception("판매자 인증 대기 중인 사용자입니다.");
			}
			else if(seller.isPresent()) {
				throw new Exception("이미 판매자 인증된 사용자입니다.");
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public void modify(ProcessDTO dto) throws Exception {
		try {
			// 판매자 인증 신청서 가져오기
			Optional<SellerAuths> sellerAuthOpt = applyRepository.findById(dto.getSellerAuthPk());
			String process = dto.getProcess();
			if(sellerAuthOpt.isEmpty()) {
				throw new Exception("유효하지 않은 판매자 인증 신청서입니다.");
			}
			else if(sellerAuthOpt.get().getAuth() != ApplyType.Processing) {
				throw new Exception("이미 처리된 신청서입니다.");
			}
			else {
				SellerAuths apply = sellerAuthOpt.get();
				if(process.equals("Accepted")) {
					apply.setAuth(ApplyType.Accepted);
					Users user = apply.getUser();
					Sellers seller = new Sellers();
					seller.setUser(user);
					sellerRepository.save(seller);
				}
				else if(process.equals("Duplicated")) {
					apply.setAuth(ApplyType.Duplicated);
				}
				else {
					throw new Exception("유효하지 않은 처리 입력값입니다.");
				}
				apply.setSignDate(LocalDateTime.now());;
				applyRepository.save(apply);
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	public List<ApplyDTO> getApplyList(Integer page) throws Exception{
		try {
			Pageable pageable = PageRequest.of(page-1, 20, Sort.by("id").ascending());
			Page<SellerAuths> applies = applyRepository.findAll(pageable);
			if(applies.isEmpty()) {
				throw new Exception("판매자 인증 신청서가 없습니다.");
			}
			List<ApplyDTO> applyList = new ArrayList<ApplyDTO>();
			for(SellerAuths apply : applies) {
				ApplyDTO dto = ApplyDTO.toDTO(apply);
				applyList.add(dto);
			}
			return applyList;
		}catch(Exception e) {
			throw e;
		}
	}
	
	public ApplyDTO getApply(Integer sellerAuthId) throws Exception{
		try {			
			Optional<SellerAuths> sellerAuthOpt = applyRepository.findById(sellerAuthId);
			if(sellerAuthOpt.isEmpty()) {
				throw new Exception("존재하지 않는 판매자 인증 신청서입니다.");
			}
			else {
				ApplyDTO dto = new ApplyDTO();
				SellerAuths auth = sellerAuthOpt.get();
				dto.setApplyDate(auth.getApplyDate());
				dto.setAuth(auth.getAuth());
				dto.setId(auth.getId());
				dto.setSignDate(auth.getSignDate());
				dto.setUserPk(auth.getUser().getId());
				return dto;
			}
		}catch(Exception e) {
			throw e;
		}
	}
}
