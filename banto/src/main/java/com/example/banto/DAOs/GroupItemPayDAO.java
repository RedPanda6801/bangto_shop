package com.example.banto.DAOs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.banto.Entitys.*;
import com.example.banto.Repositorys.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.banto.DTOs.GroupBuyItemDTO;
import com.example.banto.DTOs.GroupItemPayDTO;

@Component
public class GroupItemPayDAO {
	@Autowired
	AuthDAO authDAO;

	@Autowired
	GroupBuyPayRepository groupItemPayRepository;
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	GroupBuyItemRepository groupBuyItemRepository;
	@Autowired
	OptionRepository optionRepository;
	@Autowired
	WalletRepository walletRepository;

	public List<GroupItemPayDTO> getPayListByItem(GroupBuyItemDTO dto) throws Exception {
		try {
			// 현재 이벤트 찾기
			List<GroupItemPays> pays = groupItemPayRepository.findByItemId(dto.getItemId());
			// 비어있으면 빈값 주기
			if(pays.isEmpty()) {
				return new ArrayList<GroupItemPayDTO>();
			}else {
				List<GroupItemPayDTO> dtos = new ArrayList<>();
				for(GroupItemPays pay : pays) {
					dtos.add(GroupItemPayDTO.toDTO(pay));
				}
				return dtos;
			}
		}catch(Exception e) {
			throw e;
		}
	}

	@Transactional
	public void payGroupItem(Integer userId, GroupItemPayDTO dto) throws Exception {
		try {
			// 인증 및 지갑 가져오기
			Users user = authDAO.auth(userId);
			Optional<Wallets> walletOpt = walletRepository.findByUser_Id(userId);
			// 현재 이벤트 찾기
			Optional<Items> itemOpt = itemRepository.findById(dto.getItemPk());
			Optional<GroupBuyItems> groupItemOpt = groupBuyItemRepository.findById(dto.getGroupItemPk());
			System.out.println("1231231hello123123123");
			// 비어있으면 빈값 주기
			if(itemOpt.isEmpty() || groupItemOpt.isEmpty() || walletOpt.isEmpty()){
				throw new Exception("정보 조회 오류 오류");
			}
			else {
				Wallets wallet = walletOpt.get();
				GroupBuyItems groupItem = groupItemOpt.get();
				Items item = itemOpt.get();
				// 공동 구매 정보와 비교
				if(!groupItem.getItem().equals(item)) {
					throw new Exception("공동 구매 물건 오류");
				}
				Options selectedOption = new Options();
				boolean isOption = false;
				for(Options option : item.getOptions()){
					if(option.getId().equals(dto.getOptionPk())){
						selectedOption = option;
						isOption = true;
					}
				}
				if(!isOption){
					throw new Exception("옵션 정보 오류");
				}else if(dto.getAmount() - groupItem.getNowAmount() > 0){
					throw new Exception("재고 소진");
				}
				else{
					int price = (int) Math.round((item.getPrice() + selectedOption.getAddPrice()) * dto.getAmount() * 0.9);
					// 10% 할인하여 결제함(반올림)
					if(wallet.getCash() < price){
						throw new Exception("잔액 부족");
					}
					else{
						wallet.setCash(wallet.getCash() - price);
						// 결제 내역 생성
						GroupItemPays pay = new GroupItemPays();
						pay.setItem(item);
						pay.setItemTitle(item.getTitle());
						pay.setAmount(dto.getAmount());
						pay.setOptionInfo(selectedOption.getOptionInfo());
						pay.setDeliverInfo(DeliverType.Preparing);
						pay.setUser(user);
						pay.setPay(price);
						// 결제 추가
						groupItemPayRepository.save(pay);
						walletRepository.save(wallet);
						// 재고 차감
						groupItem.setNowAmount(groupItem.getNowAmount() - dto.getAmount());
						selectedOption.setAmount(selectedOption.getAmount() - dto.getAmount());
						groupBuyItemRepository.save(groupItem);
						optionRepository.save(selectedOption);
					}
				}
			}
		}catch(Exception e) {
			throw e;
		}
	}

	public List<GroupItemPayDTO> getMyGroupPayList(Integer userId, Integer year) throws Exception {
		try {
			Users user = authDAO.auth(userId);
			// 현재 이벤트 찾기
			List<GroupItemPays> pays = groupItemPayRepository.findByUserIdAndYear(userId, year);
			// 비어있으면 빈값 주기
			if(pays.isEmpty()) {
				return new ArrayList<GroupItemPayDTO>();
			}else {
				List<GroupItemPayDTO> dtos = new ArrayList<>();
				for(GroupItemPays pay : pays) {
					dtos.add(GroupItemPayDTO.toDTO(pay));
				}
				return dtos;
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
}
