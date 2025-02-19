package com.example.banto.DAOs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.banto.DTOs.GroupBuyDTO;
import com.example.banto.Entitys.GroupBuys;
import com.example.banto.Entitys.Users;
import com.example.banto.Repositorys.GroupBuyRepository;

import jakarta.transaction.Transactional;

@Component
public class GroupBuyDAO {
	@Autowired
	AuthDAO authDAO;
	@Autowired
	GroupBuyRepository groupBuyRepository;
	
	// 날짜 추가 (관리자)
	@Transactional
	public void addEvent(GroupBuyDTO dto) throws Exception{
		try {
			// 시작 날짜 조회 후 날짜 중복 확인
			Optional<GroupBuys> event = groupBuyRepository.findLatest();
			// 시작 날짜가 가장 최근에 했던 이벤트 마감일보다 빠르면 예외 처리
			if(event.isPresent() && dto.getStartDate().isBefore(event.get().getEndDate())) {
				throw new Exception("날짜 정보 오류");
			}else {
				// 날짜 추가 로직
				groupBuyRepository.save(GroupBuys.toEntity(dto));
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	public List<GroupBuyDTO> getEventList(Integer userId) throws Exception{
		try {
			// 인증 유효 확인
			if(!authDAO.authRoot(userId) && !authDAO.authSeller(userId)) {
				throw new Exception("권한 없음");
			}
			List<GroupBuys> eventList = groupBuyRepository.findAll();
			// 시작 날짜가 가장 최근에 했던 이벤트 마감일보다 빠르면 예외 처리
			List<GroupBuyDTO> dtos = new ArrayList<>();
			for(GroupBuys event : eventList) {
				dtos.add(GroupBuyDTO.toDTO(event));
			}
			return dtos;
		}catch(Exception e) {
			throw e;
		}
	}
	
	public GroupBuyDTO getCurrentEvent() throws Exception{
		try {
			Optional<GroupBuys> eventOpt = groupBuyRepository.findLatest();
			// 시작 날짜가 가장 최근에 했던 이벤트 마감일보다 빠르면 예외 처리
			if(eventOpt.isEmpty()) {
				throw new Exception("존재하는 이벤트 없음");
			}
			return GroupBuyDTO.toDTO(eventOpt.get());
		}catch(Exception e) {
			throw e;
		}
	}
}
