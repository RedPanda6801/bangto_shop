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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.example.banto.DTOs.PageDTO;
import com.example.banto.DTOs.ProcessDTO;
import com.example.banto.DTOs.ResponseDTO;

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

	public ResponseDTO getAuthInfo() throws Exception{
		try {
			// 인증 유효 확인
			Users user = authDAO.auth(SecurityContextHolder.getContext().getAuthentication());
			List<SellerAuths> sellerAuths = applyRepository.findAllByUserId(user.getId());
			if(sellerAuths.isEmpty()) {
				return new ResponseDTO(new ArrayList<SellerAuths>(), null);
			}
			else {
				List<ApplyDTO> applyList = new ArrayList<ApplyDTO>();
				for(SellerAuths auth : sellerAuths) {					
					ApplyDTO dto = ApplyDTO.toDTO(auth);
					applyList.add(dto);
				}
				return new ResponseDTO(applyList, null);
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public void applySellerAuth() throws Exception {
		try {
			Users user = authDAO.auth(SecurityContextHolder.getContext().getAuthentication());
			// 판매자 가져오기
			Optional<Sellers> seller = sellerRepository.findByUser_Id(user.getId());
			Optional<SellerAuths> processing = applyRepository.findProcessingAuthByUserId(user.getId());
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
			if(!authDAO.authRoot(SecurityContextHolder.getContext().getAuthentication())){
				throw new Exception("관리자 권한 오류");
			}
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
	
	public ResponseDTO getApplyList(Integer page) throws Exception{
		try {
			if(!authDAO.authRoot(SecurityContextHolder.getContext().getAuthentication())){
				throw new Exception("관리자 권한 오류");
			}
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
			return new ResponseDTO(applyList, new PageDTO(applies.getSize(), applies.getTotalElements(), applies.getTotalPages()));
		}catch(Exception e) {
			throw e;
		}
	}
	
	public ResponseDTO getApply(Integer sellerAuthId) throws Exception{
		try {
			if(!authDAO.authRoot(SecurityContextHolder.getContext().getAuthentication())){
				throw new Exception("관리자 권한 오류");
			}
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
				dto.setUserName(auth.getUser().getName());
				return new ResponseDTO(dto, null);
			}
		}catch(Exception e) {
			throw e;
		}
	}
}
