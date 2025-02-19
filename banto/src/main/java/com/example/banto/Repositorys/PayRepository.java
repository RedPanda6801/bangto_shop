package com.example.banto.Repositorys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.banto.Entitys.SoldItems;

public interface PayRepository extends JpaRepository<SoldItems, Integer> {
	@Query("SELECT s FROM SoldItems s WHERE s.user.id = :userId")
	public Page<SoldItems> findAllByUserId(@Param("userId") Integer userId, Pageable pageable);
	
	@Query("SELECT s FROM SoldItems s WHERE s.storePk = :storeId")
	public Page<SoldItems> findAllByStoreId(@Param("storeId") Integer storeId, Pageable pageable);
}
