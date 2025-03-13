package com.example.banto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import com.example.banto.Entitys.Options;
import com.example.banto.Repositorys.OptionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.banto.Entitys.Items;
import com.example.banto.Entitys.Stores;
import com.example.banto.Repositorys.ItemRepository;
import com.example.banto.Repositorys.StoreRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
class ItemDAOTest {
	@Autowired
	StoreRepository storeRepository;
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	OptionRepository optionRepository;
	@AfterEach
	public void after() {
		System.out.println("아이템 테스트 종료");
	}
	
	@BeforeEach
	public void before() {
		System.out.println("아이템 테스트 시작");
	}
	
	@Test
	@Transactional
	void getStoreSuccessTest() {
		System.out.println("성공 메소드 - input : 1");
		int id = 1;
		
		// 인증 유효 확인
		Optional<Stores> store = storeRepository.findById(id);
		
		Assertions.assertThat(store.get().getItems()).isNotNull();
	}
	@Test
	@Transactional
	void getStorefailedTest() {
		System.out.println("실패 메소드 - input : 2");
		int id = 2;
		
		// 인증 유효 확인
		Optional<Stores> store = storeRepository.findById(id);
		
		Assertions.assertThat(store.get().getItems()).isNotNull();
	}

	@Test
	@Transactional
	void optionDeleteTest() {
		int id = 2;

		// 인증 유효 확인
		Optional<Options> option = optionRepository.findById(id);
		optionRepository.delete(option.get());

	}
	
	@Test
	@Transactional
	void get20ItemTest() {
		int page = 1;
		
		Pageable pageable = PageRequest.of(page-1, 20, Sort.by("id").ascending());
		Page<Items>items = itemRepository.findAll(pageable);
		
		Assertions.assertThat(items.getContent().size()).isEqualTo(20);
	}
}
