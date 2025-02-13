package com.example.banto.Repositorys;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.banto.Entitys.Items;

public interface ItemRepository extends JpaRepository<Items, Integer> {
	
	@Query("SELECT i FROM Items i WHERE i.store.id = :storeId")
	Page<Items> getItemsByStoreId(@Param("storeId") Integer storeId, Pageable pageable); 

}
