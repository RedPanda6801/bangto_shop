package com.example.banto.DAOs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.example.banto.DTOs.PayDTO;
import com.example.banto.DTOs.SoldItemDTO;
import com.example.banto.Entitys.Carts;
import com.example.banto.Entitys.DeliverType;
import com.example.banto.Entitys.Items;
import com.example.banto.Entitys.Options;
import com.example.banto.Entitys.SoldItems;
import com.example.banto.Entitys.Users;
import com.example.banto.Entitys.Wallets;
import com.example.banto.Repositorys.CartRepository;
import com.example.banto.Repositorys.ItemRepository;
import com.example.banto.Repositorys.OptionRepository;
import com.example.banto.Repositorys.PayRepository;
import com.example.banto.Repositorys.WalletRepository;

import jakarta.transaction.Transactional;

@Component
public class PayDAO {
	@Autowired
	PayRepository payRepository;
	@Autowired
	CartRepository cartRepository;
	@Autowired
	OptionRepository optionRepository;
	@Autowired
	WalletRepository walletRepository;
	@Autowired
	AuthDAO authDAO;
	
	@Transactional
	public void payCart(Integer userId, PayDTO dto) throws Exception{
		try {
			// 인증 유효 확인
			Users user = authDAO.auth(userId);
			List<Integer> cartPks = dto.getCartPks();
			if(cartPks.isEmpty()) {
				throw new Exception("결제할 장바구니가 입력돼야 합니다.");
			}
			else {
				// 유효하지 않은 결제가 있는지 먼저 확인
				int totalPrice = 0;
				for(Integer cartPk : cartPks) {
					Optional<Carts> cartOpt = cartRepository.findById(cartPk);
					if(cartOpt.isEmpty()) {
						throw new Exception("유효하지 않은 장바구니가 있습니다.");
					}
					if(cartOpt.get().getUser().getId() != userId) {
						throw new Exception("접근할 수 없는 장바구니가 있습니다.");
					}
					if(cartOpt.get().getAmount() > cartOpt.get().getOption().getAmount()) {
						throw new Exception("수량이 부족한 물품이 있습니다.");
					}
					totalPrice += (cartOpt.get().getItem().getPrice() + cartOpt.get().getOption().getAddPrice()) * cartOpt.get().getAmount();
				}
				
				// 배송비 로직
				if (totalPrice < 30000) {
					// 기본 5000원
					totalPrice += 5000;
				}
				else if (totalPrice >= 30000 && totalPrice < 50000) {
					// 30000원 이상 결제 시 2500원
					// 50000원 이상 결제 시 무료
					totalPrice += 2500;
				}
				
				if(dto.getUsingCashBack() < 0) {
					throw new Exception("사용할 캐시백은 음수가 될 수 없습니다.");
				}
				Optional<Wallets> walletOpt = walletRepository.findByUser_Id(userId);
				if(walletOpt.isEmpty()) {
					throw new Exception("지갑이 없는 사용자입니다.");
				}
				Wallets wallet = walletOpt.get();
				if(dto.getUsingCashBack() > wallet.getCashBack()) {
					throw new Exception("캐시백이 부족합니다.");
				}
				if(totalPrice > wallet.getCash() + dto.getUsingCashBack()) {
					throw new Exception("잔액이 부족합니다(배송비, 캐시백 포함).");
				}
				Integer usingCash = totalPrice - dto.getUsingCashBack();
				wallet.setCash(wallet.getCash() - usingCash);
				wallet.setCashBack(wallet.getCashBack() - dto.getUsingCashBack());
				walletRepository.save(wallet);
				for(Integer cartPk : cartPks) {
					Optional<Carts> cartOpt = cartRepository.findById(cartPk);
					Carts cart = cartOpt.get();
					SoldItems soldItem = new SoldItems();
					soldItem.setAddPrice(cart.getOption().getAddPrice());
					soldItem.setAmount(cart.getAmount());
					soldItem.setDeliverInfo(DeliverType.Preparing);
					soldItem.setItemName(cart.getItem().getTitle());
					soldItem.setItemPk(cart.getItem().getId());
					soldItem.setItemPrice(cart.getItem().getPrice());					soldItem.setOptionInfo(cart.getOption().getOptionInfo());
					soldItem.setOptionPk(cart.getOption().getId());
					soldItem.setSoldPrice((cart.getItem().getPrice() + cart.getOption().getAddPrice()) * cart.getAmount());
					soldItem.setUser(user);
					Options option = cart.getOption();
					option.setAmount(option.getAmount() - cart.getAmount());
					optionRepository.save(option);
					cartRepository.delete(cart);
					payRepository.save(soldItem);
				}
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	public List<SoldItemDTO> getPayList(Integer userId, Integer page) throws Exception{
		try {
			if(userId == -1) {				
				// 인증 유효 확인
				authDAO.auth(userId);
			}
			Pageable pageable = PageRequest.of(page-1, 20, Sort.by("id").ascending());
			Page<SoldItems> soldItems = payRepository.findAllByUserId(userId, pageable);
			List<SoldItemDTO> soldItemList = new ArrayList<SoldItemDTO>();
			if(soldItems.isEmpty()) {
				throw new Exception("결제 내역이 없습니다.");
			}
			else {
				for(SoldItems soldItem : soldItems) {
					SoldItemDTO dto = SoldItemDTO.toDTO(soldItem);
					soldItemList.add(dto);
				}
				return soldItemList;
			}
		}catch(Exception e) {
			throw e;
		}
	}
}
