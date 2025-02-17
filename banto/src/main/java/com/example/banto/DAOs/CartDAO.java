package com.example.banto.DAOs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.banto.DTOs.CartDTO;
import com.example.banto.Entitys.Carts;
import com.example.banto.Entitys.Items;
import com.example.banto.Entitys.Options;
import com.example.banto.Entitys.Users;
import com.example.banto.Repositorys.CartRepository;
import com.example.banto.Repositorys.ItemRepository;
import com.example.banto.Repositorys.OptionRepository;

import jakarta.transaction.Transactional;

@Component
public class CartDAO {
	@Autowired
	CartRepository cartRepository;
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	OptionRepository optionRepository;
	@Autowired
	AuthDAO authDAO;
	
	@Transactional
	public void addCart(Integer userId, CartDTO dto) throws Exception{
		try {
			// 인증 유효 확인
			Users user = authDAO.auth(userId);
			Integer itemPk = dto.getItemPk();
			Integer optionPk = dto.getOptionPk();
			Optional<Items> itemOpt = itemRepository.findById(itemPk);
			if(itemOpt.isEmpty()) {
				throw new Exception("존재하지 않는 물품입니다.");
			}
			Optional<Options> optionOpt = optionRepository.findById(optionPk);
			if(optionOpt.isEmpty()) {
				throw new Exception("존재하지 않는 옵션입니다.");
			}
			if(dto.getAmount()<= 0) {
				throw new Exception("물품 수량은 자연수이어야 합니다.");
			}
			// 물품과 옵션이 같은 항목이 이미 장바구니에 있을 경우 수량만 추가
			List<Carts> carts = cartRepository.findAllByUserId(userId);
			for(Carts cart : carts) {
				if(cart.getItem().getId() == itemPk && cart.getOption().getId() == optionPk) {
					cart.setAmount(cart.getAmount() + dto.getAmount());
					cartRepository.save(cart);
					return;
				}
			}
			Carts cart = new Carts();
			cart.setAmount(dto.getAmount());
			cart.setItem(itemOpt.get());
			cart.setOption(optionOpt.get());
			cart.setUser(user);
			cartRepository.save(cart);
		}catch(Exception e) {
			throw e;
		}
	}
	
	public List<CartDTO> readCart(Integer userId) throws Exception{
		try {
			// 인증 유효 확인
			authDAO.auth(userId);
			List<Carts> carts = cartRepository.findAllByUserId(userId);
			if(carts.isEmpty()) {
				throw new Exception("장바구니가 비었습니다.");
			}
			else {
				List<CartDTO> cartList = new ArrayList<CartDTO>();
				for(Carts cart : carts) {
					CartDTO dto = new CartDTO();
					dto.setAmount(cart.getAmount());
					dto.setCartPk(cart.getId());
					dto.setItemPk(cart.getItem().getId());
					dto.setOptionPk(cart.getOption().getId());
					dto.setUserPk(cart.getUser().getId());
					cartList.add(dto);
				}
				return cartList;
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public void modifyCart(Integer userId, CartDTO dto) throws Exception{
		try {
			// 인증 유효 확인
			authDAO.auth(userId);
			Optional<Carts> cartOpt = cartRepository.findById(dto.getCartPk());
			if(cartOpt.isEmpty()) {
				throw new Exception("존재하지 않는 장바구니 항목입니다.");
			}
			else if(cartOpt.get().getUser().getId() != userId) {
				throw new Exception("다른 사용자의 장바구니입니다.");
			}
			Integer optionPk = dto.getOptionPk();
			Integer amount = dto.getAmount();
			// 옵션이나 수량만 바꿀 수 있음
			if(optionPk == null && amount == null) {
				throw new Exception("수정할 내용이 없습니다.");
			}
			else {
				Carts cart = cartOpt.get();
				if(optionPk != null) {					
					Optional<Options> optionOpt = optionRepository.findById(optionPk);
					if(optionOpt.isEmpty()) {
						throw new Exception("존재하지 않는 옵션입니다.");
					}
					else {
						cart.setOption(optionOpt.get());
					}
				}
				if(amount != null) {					
					if(dto.getAmount()<= 0) {
						throw new Exception("물품 수량은 자연수이어야 합니다.");
					}
					cart.setAmount(dto.getAmount());
				}
				cartRepository.save(cart);
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public void deleteCart(Integer userId, CartDTO dto) throws Exception{
		try {
			// 인증 유효 확인
			authDAO.auth(userId);
			Optional<Carts> cartOpt = cartRepository.findById(dto.getCartPk());
			if(cartOpt.isEmpty()) {
				throw new Exception("존재하지 않는 장바구니 항목입니다.");
			}
			else if(cartOpt.get().getUser().getId() != userId) {
				throw new Exception("다른 사용자의 장바구니입니다.");
			}
			else {
				cartRepository.delete(cartOpt.get());
			}
		}catch(Exception e) {
			throw e;
		}
	}
}
